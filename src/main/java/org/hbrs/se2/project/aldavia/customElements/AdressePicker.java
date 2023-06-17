package org.hbrs.se2.project.aldavia.customElements;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.TextField;



public class AdressePicker extends CustomField<String> {
    private TextField strasse = new TextField("Strasse");
    private TextField hsnr = new TextField("HausNr");
    private TextField plz = new TextField("PLZ");
    private TextField ort = new TextField("Ort");
    private TextField land = new TextField("Land");




    public AdressePicker() {
        add(strasse, new Text("_"), hsnr,new Text("_"), plz, new Text("_"), ort,new Text("_"), land);




    }

    @Override
    protected String generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(String s) {

    }


}
