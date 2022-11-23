package pl.coderslab.pokersessionmanager;

import java.time.LocalDate;
import java.time.Period;

public class MainTraining {
    public static void main(String[] args) {
        Period period = Period.between(LocalDate.of(1999,11,1),LocalDate.now());
        System.out.println(period.toTotalMonths());
    }
}
