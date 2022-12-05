package net.cbolik.randomizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class DefaultController {

    @Value("${lists.directory}")
    private String listsDir = "";

    @GetMapping("/")
    public String greeting(@RequestParam(name="file", required=false, defaultValue="MOTD") String file, Model model) {
        String randomLine = "";

        model.addAttribute("file", file);

        // Read file - cwd is project root dir
        String fileRelPath = listsDir + "/" + file + ".list";
        try (BufferedReader in = new BufferedReader(new FileReader(fileRelPath))) {
            // Get random line
            randomLine = "Here's your Motto of the Day!";

        } catch (FileNotFoundException e) {
            model.addAttribute("fileRelPath", fileRelPath);
            model.addAttribute("error", "file not found");
            return "errorPage";
        } catch (IOException e) {
            model.addAttribute("error", "I/O exception");
            return "errorPage";
        }

        model.addAttribute("randomLine", randomLine);
        return "randomized";
    }
}