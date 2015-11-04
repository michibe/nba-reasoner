package org.hhz.nbareasoner.cbr.impl.phase.retrieve.similarity;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hhz.nbareasoner.cbr.model.phase.retrieve.CbrRetrieveResult;

import org.hhz.nbareasoner.cbml.model.base.data.CCase;
import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;
import org.hhz.nbareasoner.service.jsonserializer.SimilarityResultJacksonSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by mbehr on 17.02.2015.
 *
 * Bildet die grundlage auf der später die nba berechnet werden kann... alle Fälle die ähnlich dem akutellen fall sind
 * Retrieve result sollte auch leicht serialisierbar sein
 *
 */
@JsonSerialize(using=SimilarityResultJacksonSerializer.class)
public class SimilarityResult implements CbrRetrieveResult{
    //Achtung die CaseBase kann im verlauf geändert werden, wenn es sich nur um eine referenz handelt... man könnte ja auch alle fälle komplett kopieren und
    //neuen Log erstellen, das würde aber zu viel Arbeitsspeicher kosten... es muss eben darauf geachtet werden, dass die CaseBase nur erneuert wird, wenn
    //Keine aktionen mehr darauf ausgeführt werden
    @NotNull
    private final CCaseBase underlyingECaseBase;

    //Hierbei handelt es sich um den aktiven fall
    @NotNull
    private final CCase queryCase;

    @NotNull
    private final List<SimilarCase> similarCases;

    public SimilarityResult(@NotNull CCaseBase underlyingECaseBase, @NotNull CCase queryCase, @NotNull List<SimilarCase> similarCases) {
        this.underlyingECaseBase = underlyingECaseBase;
        this.queryCase = queryCase;
        this.similarCases = similarCases;
    }




    public List<SimilarCase> getSimilarCases() {
        return Collections.unmodifiableList(similarCases);
    }



    public CCase getQueryCase() {
        return queryCase;
    }

    @NotNull
    public CCaseBase getUnderlyingCaseBase() {
        return underlyingECaseBase;
    }

    @Override
    public String toString() {

        String similarCasesString="";

        for(SimilarCase similarCase : this.similarCases)
        {
            similarCasesString = similarCasesString + similarCase.toString() + "\n";
        }

        return "SimilarityResult{" +
                "similarCases=" + similarCasesString +
                '}';
    }




}
