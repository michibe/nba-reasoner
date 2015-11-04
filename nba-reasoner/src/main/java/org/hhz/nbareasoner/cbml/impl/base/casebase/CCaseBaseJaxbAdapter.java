package org.hhz.nbareasoner.cbml.impl.base.casebase;

import org.hhz.nbareasoner.cbml.model.base.data.CCaseBase;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by mbehr on 21.05.2015.
 *
 * Dieser Adapter wird benötigt um die Fallbasis aufzubauen.
 * Die einzelnen Ojekte sind unterienander abhängig, sodass eine Art Builder benötigt wird.
 *
 *
 * ECaseBaseForJaxb wird von Jaxb automtaisch gefüllt. Allerdings benötigen wir ein kompliziertes Setup der Fallbasis mit der Möglichkeit zum Zugriff auf Elternkomponenten
 * und VorgänderAktionen und FOlgeaktionen etc. Aus diesem Grund wird beim Unmarshallung (Einlesen der XML) die build() mehtode aufgerufen
 *
 */
public class CCaseBaseJaxbAdapter extends XmlAdapter<CCaseBaseForJaxb,CCaseBase> {

    //
    @Override
    public CCaseBase unmarshal(CCaseBaseForJaxb v) throws Exception {

        return v.build();
    }

    @Override
    public CCaseBaseForJaxb marshal(CCaseBase v) throws Exception {




        return new CCaseBaseForJaxb(v);
    }
}
