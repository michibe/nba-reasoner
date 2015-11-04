package org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.predefinition;

import org.hhz.nbareasoner.cbml.impl.base.definition.attributedefinition.CAttributeDefinitionImplAbs;
import org.hhz.nbareasoner.cbml.model.base.CException;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CKeyAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.alias.CValueAlias;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinedAttributeDefinition;
import org.hhz.nbareasoner.cbml.model.base.definition.attributedefinition.predefinition.CPredefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;


/**
 * Created by mbehr on 27.04.2015.
 */
@XmlRootElement(name="predefinedAttributeDefinition")
public class CPredefinedAttributeDefinitionImpl extends CAttributeDefinitionImplAbs implements CPredefinedAttributeDefinition {

    private final static Logger logger = LoggerFactory.getLogger(CPredefinedAttributeDefinitionImpl.class);


    @XmlElement(type=CPredefinitionImpl.class)
    private final CPredefinition predefinition;

    @Nullable
    @XmlAttribute(name="converterMethod")
    private final String converterMethod;

    @Nullable
    private Constructor<?> typeConstructor;

    @Nullable
    private Method typeConverterMethod;

    public CPredefinedAttributeDefinitionImpl(@NotNull String key, String type, @Nullable String converterMethod, String value, List<CKeyAlias> eKeyAliasDefinitionsTmp, List<CValueAlias> eValueAliasDefinitionsTmp, CPredefinition predefinition) {
        super(key, type, value, eKeyAliasDefinitionsTmp, eValueAliasDefinitionsTmp);
        this.predefinition = predefinition;

        this.converterMethod =converterMethod;

        try {
            if(converterMethod==null)
            {
                this.initializeTypeConstructor();
            }
            else
            {
                this.initializeConverterMethod();
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not create CAttributeDefinitionImplAbs() due to:", e);
        }

    }

    private CPredefinedAttributeDefinitionImpl() {
        super();
        this.predefinition = null;
        this.converterMethod = null;
    }



    @Override
    public CPredefinition getPredefinition() {
        return predefinition;
    }



    public <E> E convertValueAsDeclaredType(String value) throws CException {
        E object = null;

        if(this.typeConverterMethod!=null)
        {
            try {
                object = (E) this.typeConverterMethod.invoke(null, value);
            } catch (Exception e) {
                throw new CException("Could not getValueAsDeclared Type via ConverterMethod  due to ", e);
            }
        }
        else
        {
            try {
                object = (E) this.typeConstructor.newInstance(value);
            } catch (Exception e) {
                throw new CException("Could not getValueAsDeclared Type via TypeConstructor  due to ", e);
            }
        }


        return object;

    }

    private void initializeTypeConstructor() throws CException {
        logger.debug("initializeTypeConstructor");
        try {
            Class clazz = Class.forName(this.getType());
            this.typeConstructor = clazz.getConstructor(String.class);



        } catch (ClassNotFoundException e) {
            throw new CException("Could not find java Class for type declaration '" + this.getType() + " ' for attribute '"+ this.getKey() + "'",e);
        } catch (NoSuchMethodException e) {
            throw new CException("Could not find Constructor (Constructor(String)) for type declaration '" + this.getType() + " ' for attribute '"+ this.getKey() + "'",e);
        }


    }

    private void initializeConverterMethod() throws CException {
        logger.debug("initializeConverterMethod");

        if(this.converterMethod!=null)
        {
            Class clazz = null;
            String className = this.converterMethod.substring(0,this.converterMethod.lastIndexOf("."));
            String methodName= this.converterMethod.substring(this.converterMethod.lastIndexOf(".")+1,this.converterMethod.length());

            try {

                clazz = Class.forName(className);
                this.typeConverterMethod = clazz.getMethod(methodName, String.class);



            } catch (ClassNotFoundException e) {
                throw new CException("Could not find java Class '"+className+"'  for converterMethodDeclaration '" + this.converterMethod + "' for attribute '"+ this.getKey() + "' de to: ",e);
            } catch (NoSuchMethodException e) {
                throw new CException("Could not find Converter method '" + methodName +  "' for converterMethodDeclaration '" + this.converterMethod + "' for attribute '"+ this.getKey() + "' due to:",e);

            }catch (Exception e)
            {
                throw new CException("Could not find Converter method '" + methodName +  "' for converterMethodDeclaration '" + this.converterMethod + "' for attribute '"+ this.getKey() + "' due to:",e);
            }

        }

    }

    private void afterUnmarshal(Unmarshaller u, Object parent) throws CException {
        //TODO checken ob das noch hbenötigt wird
        logger.debug("afterUnmarshal for " + this.getClass().getSimpleName() + " called");
        if(converterMethod==null)
        {
            this.initializeTypeConstructor();
        }
        else
        {
            this.initializeConverterMethod();
        }
    }

}
