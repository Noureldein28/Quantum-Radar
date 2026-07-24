package com.fawry.radar.service;

import com.fawry.radar.model.Fine;
import com.fawry.radar.model.Violation;


public class FinePrinter {

    public String format(Fine fine) {
        StringBuilder sb = new StringBuilder();
        sb.append("Traffic fine for car ").append(fine.getPlateNumber()).append(System.lineSeparator());
        sb.append("Total amount: ").append(formatAmount(fine.getTotalAmount())).append(" EGP").append(System.lineSeparator());
        sb.append("Violations:").append(System.lineSeparator());
        for (Violation v : fine.getViolations()) {
            sb.append("- ").append(v.getDescription())
                    .append(" : ").append(formatAmount(v.getFee())).append(" EGP")
                    .append(System.lineSeparator());
        }
        return sb.toString().stripTrailing();
    }

    public void print(Fine fine) {
        System.out.println(format(fine));
    }

    private String formatAmount(double amount) {
        if (amount == Math.floor(amount)) {
            return String.valueOf((long) amount);
        }
        return String.valueOf(amount);
    }
}
