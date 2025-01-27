package com.example.randomnumbers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class RandomNumbersController {

    @GetMapping("/")
    public String home() {
        return "home"; // возвращает страницу с формой ввода диапазона
    }

    @GetMapping("/generate")
    public String generateRandomNumbers(@RequestParam("min") int min,
                                        @RequestParam("max") int max,
                                        Model model) {
        if (min >= max) {
            model.addAttribute("error", "Минимальное значение должно быть меньше максимального!");
            return "home";
        }

        List<Integer> randomNumbers = new ArrayList<>();
        Random random = new Random();
        int sum = 0;

        for (int i = 0; i < 1000; i++) {
            int number = random.nextInt((max - min) + 1) + min;
            randomNumbers.add(number);
            sum += number;
        }

        double average = sum / 1000.0;

        model.addAttribute("numbers", randomNumbers);
        model.addAttribute("average", average);

        return "result"; // возвращает страницу с результатами
    }
}