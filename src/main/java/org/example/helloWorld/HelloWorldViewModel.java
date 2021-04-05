package org.example.helloWorld;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloWorldViewModel implements ViewModel{

    private StringProperty helloMessage = new SimpleStringProperty("Hello World");

    public StringProperty helloMessageProperty() {
        return helloMessage;
    }

    public String getHelloMessage() {
        return helloMessage.get();
    }

    public void setHelloMessage(String message) {
        helloMessage.set(message);
    }

}
