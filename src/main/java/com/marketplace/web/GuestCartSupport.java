package com.marketplace.web;

import com.marketplace.dto.GuestCartLine;
import com.marketplace.entity.Piece;
import com.marketplace.entity.PrixPiece;
import com.marketplace.repository.PrixPieceRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class GuestCartSupport {

    public static final String SESSION_KEY = "mpd_guest_cart";

    private GuestCartSupport() {
    }

    @SuppressWarnings("unchecked")
    public static List<GuestCartLine> getLines(HttpSession session) {
        Object raw = session.getAttribute(SESSION_KEY);
        if (raw instanceof List) {
            return (List<GuestCartLine>) raw;
        }
        List<GuestCartLine> lines = new ArrayList<>();
        session.setAttribute(SESSION_KEY, lines);
        return lines;
    }

    public static int countItems(HttpSession session) {
        return getLines(session).stream().mapToInt(GuestCartLine::getQuantite).sum();
    }

    public static int totalFcfa(HttpSession session) {
        return getLines(session).stream().mapToInt(GuestCartLine::getSousTotal).sum();
    }

    public static void addPiece(HttpSession session, Piece piece, int qty, PrixPieceRepository prixPieceRepository) {
        if (piece == null || piece.getIdpiece() == null || qty < 1) {
            return;
        }
        int prix = resolvePrice(piece.getIdpiece(), prixPieceRepository);
        List<GuestCartLine> lines = getLines(session);
        Optional<GuestCartLine> existing = lines.stream()
                .filter(l -> piece.getIdpiece().equals(l.getIdpiece()))
                .findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantite(existing.get().getQuantite() + qty);
        } else {
            lines.add(new GuestCartLine(
                    piece.getIdpiece(),
                    piece.getLibelle(),
                    piece.getRef(),
                    qty,
                    prix
            ));
        }
    }

    public static void updateQty(HttpSession session, Integer idpiece, int qty) {
        List<GuestCartLine> lines = getLines(session);
        if (qty <= 0) {
            lines.removeIf(l -> idpiece.equals(l.getIdpiece()));
            return;
        }
        lines.stream().filter(l -> idpiece.equals(l.getIdpiece())).findFirst()
                .ifPresent(l -> l.setQuantite(qty));
    }

    public static void remove(HttpSession session, Integer idpiece) {
        getLines(session).removeIf(l -> idpiece.equals(l.getIdpiece()));
    }

    public static void clear(HttpSession session) {
        session.removeAttribute(SESSION_KEY);
    }

    public static int resolvePrice(Integer idpiece, PrixPieceRepository prixPieceRepository) {
        List<PrixPiece> prixList = prixPieceRepository.findByPieceIdpiece(idpiece);
        if (prixList == null || prixList.isEmpty()) {
            return 0;
        }
        PrixPiece p = prixList.get(0);
        if (p.getVentereduit() != null && p.getVentereduit() > 0) {
            return p.getVentereduit();
        }
        if (p.getVente() != null && p.getVente() > 0) {
            return p.getVente();
        }
        return p.getAchat() != null ? p.getAchat() : 0;
    }
}
