package com.example.application.views.about;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    public static class Horse {
        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int age;
        private String name;
    }

    public AboutView() {
        GridPro<Horse> gridPro = new GridPro<>();
        gridPro.setItems(getHorses());
        gridPro.addEditColumn(Horse::getName)
                .text((horse, newName) -> horse.setName(newName))
                .setHeader("Name");
        gridPro.addEditColumn(Horse::getAge)
                .custom(new IntegerField("Age"), (horse, age) -> horse.setAge(age))
                .setHeader("Age");
        gridPro.setEditOnClick(true);
        add(gridPro);
    }

    private List<Horse> getHorses() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Horse horse = new Horse();
            list.add(horse);
            horse.setAge(i);
            horse.setName("Horse " + i);
        }
        return list;
    }

}
