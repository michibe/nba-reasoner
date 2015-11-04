package org.hhz.nbareasoner.cbml.model.base.data.extern;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by m on 03.04.2015.
 */



public interface CCCaseBase extends CCComponent {



    @Nullable
    public CCCase getEClosedCase(String caseId);

    @NotNull
    public List<? extends CCCase> getECasesAsList();



}
