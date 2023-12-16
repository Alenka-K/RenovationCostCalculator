package com.example.renovationcostcalculator.model.room;

import com.example.renovationcostcalculator.model.Door;
import com.example.renovationcostcalculator.model.Form;
import com.example.renovationcostcalculator.model.utils.Count;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class FreeFormRoom extends Room{

    private final Form form = Form.FREE;

    private double lengthOfCircumscribedRectangle;
    private double widthOfCircumscribedRectangle;

    private String listOfWallLengths;

    private String rectanglesToExtractFromCircumscribedRectangle;

    @Override
    public double getFloorArea() {
        double area = lengthOfCircumscribedRectangle*widthOfCircumscribedRectangle;

        if(rectanglesToExtractFromCircumscribedRectangle != null) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(rectanglesToExtractFromCircumscribedRectangle.split("\\s*,\\s*")));
            for (String str : list) {
                double temp = Arrays.stream(str.split("/")).mapToDouble(Double::parseDouble).reduce(1, (a, b) -> a * b);

                area = area - temp;

            }
        }

        return  Count.rounding(area/1000000);

    }

    @Override
    public double getFloorPerimeter() {
        List<Door> doors = super.getDoors();
        ArrayList<String> list = new ArrayList<>(Arrays.asList(listOfWallLengths.split("\\s*,\\s*")));
        double perimeter = list.stream().mapToDouble(Double::parseDouble).sum();

        return Count.rounding(perimeter/1000);
    }

    @Override
    public double getCeilingArea() {
        double area = lengthOfCircumscribedRectangle*widthOfCircumscribedRectangle;
        if(rectanglesToExtractFromCircumscribedRectangle != null) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(rectanglesToExtractFromCircumscribedRectangle.split("\\s*,\\s*")));
            for (String str : list) {
                area = area - Arrays.stream(str.split("/")).mapToDouble(Double::parseDouble).reduce(1, (a, b) -> a * b);
            }
        }

        return Count.rounding(area/1000000);
    }

    @Override
    public double getCeilingPerimeter() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(listOfWallLengths.split("\\s*,\\s*")));
        return list.stream().mapToDouble(Double::parseDouble).sum()/1000;
    }

    @Override
    public String toString() {
        return
                "form=" + form +
                "Длина описанного: " + lengthOfCircumscribedRectangle +
                "Ширина описанного: " + widthOfCircumscribedRectangle +
                "Длины стен: " + listOfWallLengths +
                "Прямоугольники- " + rectanglesToExtractFromCircumscribedRectangle;
    }
}
