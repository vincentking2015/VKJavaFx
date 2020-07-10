package com.vk.view;

import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

@Getter
@Setter
public abstract class DashboardView implements Initializable,closable {

    protected ResourceBundle bundle;


}
