package org.hhz.nbareasoner.cbml.model.base.data;


import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.CValidationException;
import org.hhz.nbareasoner.cbml.model.base.Cbml;
import org.hhz.nbareasoner.cbml.model.base.data.extern.CCCaseBase;
import org.hhz.nbareasoner.cbml.model.base.definition.CCaseBaseDefinition;
import org.hhz.nbareasoner.util.MapList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.JAXBException;
import java.io.OutputStream;

/**
 * Created by m on 03.04.2015.
 */



public interface CCaseBase extends CComponent, CCCaseBase {


    @NotNull
    public Cbml getParentCComponent();

    @NotNull
    public CCaseBaseDefinition getCDefinition();


    @Nullable
    public CCase getEClosedCase(@NotNull String caseId);

    @Nullable
    public CCase getEOpenedCase(@NotNull String caseId) throws CException;

    @Nullable
    public CCase getECase(@NotNull String caseId) throws CException;

    @NotNull
    public MapList<String,CCase> getCCases();

    @NotNull
    public CCaseBase addECase(@NotNull CCase eCase);

    public void serializeToOutputStream(@NotNull OutputStream outputStream) throws JAXBException;


    //Renew Complete CaseBase
    public void renew() throws CException;

    //sort Cases via date
    public void sort();

    public void validateAfterLoadings() throws CValidationException;
    public void validateAfterPredefinitions() throws CValidationException;
    public void validateAfterComputations() throws CValidationException;

}
