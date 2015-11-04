package org.hhz.nbareasoner.cbr.impl.phase.reuse.nba;


import org.hhz.nbareasoner.cbml.model.base.data.CActivity;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCActivity;
import org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity.SimilarCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by mbehr on 12.03.2015.
 *
 * Activity
 *
 */
public class NbaProposalOccurrence {

   // schlagen wir nur aktivitäten vor oder auch eintreffende events ? nba ist auf jedenfall activity sonst müste es nbe oder next waarhscheinliches event heißen
    @NotNull
    private final SimilarCase relatedSimilarCase;
    @NotNull
        private final CCActivity fromActivity;
    @Nullable
        private final CCActivity toActivity;

    public NbaProposalOccurrence(@NotNull SimilarCase relatedCase, @NotNull CActivity fromActivity, @Nullable CActivity toActivity) {
        this.relatedSimilarCase = relatedCase;
        this.fromActivity = fromActivity;
        this.toActivity = toActivity;
    }


    public SimilarCase getRelatedSimilarCase() {
        return relatedSimilarCase;
    }


    public CCActivity getFromActivity() {
        return fromActivity;
    }

    @Nullable
    public CCActivity getToActivity() {
        return toActivity;
    }

    @Override
    public String toString() {
        return "NbaProposalOccurrence{" +
                "relatedSimilarCase=" + relatedSimilarCase +
                ", fromActivity=" + fromActivity +
                ", toActivity=" + toActivity +
                '}';
    }
}
