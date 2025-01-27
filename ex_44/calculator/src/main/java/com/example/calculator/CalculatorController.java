package com.example.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    private final CalculationService calculationService;

    public CalculatorController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/")
    public String showCalculator(Model model) {
        model.addAttribute("calculations", calculationService.getAllCalculations());
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam String expression, Model model) {
        Calculation calculation = calculationService.calculate(expression);
        model.addAttribute("calculation", calculation);
        model.addAttribute("calculations", calculationService.getAllCalculations());
        return "calculator";
    }
}
