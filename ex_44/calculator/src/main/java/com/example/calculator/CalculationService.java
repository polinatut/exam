package com.example.calculator;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationService {

    private final CalculationRepository calculationRepository;

    public CalculationService(CalculationRepository calculationRepository) {
        this.calculationRepository = calculationRepository;
    }

    public Calculation calculate(String expression) {
        String result;
        try {
            result = String.valueOf(evaluateExpression(expression));
        } catch (Exception e) {
            result = "Error";
        }

        Calculation calculation = new Calculation();
        calculation.setExpression(expression);
        calculation.setResult(result);
        return calculationRepository.save(calculation);
    }

    public List<Calculation> getAllCalculations() {
        return calculationRepository.findAll();
    }

    private double evaluateExpression(String expression) {
        // Простейший парсер для арифметических выражений
        expression = expression.replaceAll("\\s+", "");
        String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
        double result = Double.parseDouble(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            double operand = Double.parseDouble(tokens[i + 1]);
            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    result /= operand;
                    break;
                default:
                    throw new IllegalArgumentException("Недопустимая операция: " + operator);
            }
        }
        return result;
    }
}