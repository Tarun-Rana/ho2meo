package com.dit.Homeo.Controller;

import com.dit.Homeo.Model.Disease;
import com.dit.Homeo.Model.Patient;
import com.dit.Homeo.Model.SearchInputs;
import org.springframework.ui.Model;
import com.dit.Homeo.Service.DiseaseService;
import com.dit.Homeo.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DiseaseService diseaseService;

    public PatientController() {
    }

    @GetMapping(value="/")
    public String home() {
        return "index";
    }

    @GetMapping(value="/addPatient")
    public String addPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "/addPatient";
    }

    @GetMapping(value="/about")
    public String about() {
        return "/about";
    }

    @GetMapping(value="/search")
    public String search(Model model) {
        model.addAttribute("search", new SearchInputs());
        return "/search";
    }
    @GetMapping(value = "/prescription")
    public String precriptionPage(){
        return "/prescription";
    }


    @PostMapping("/addp")
    public boolean addP(Patient patient){
        return patientService.save(patient);
    }

    @PostMapping(value="/addPatient")
    public String addPatient(Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/add-user";
        }

        if(patientService.save(patient))
            System.out.println("saved patient");
        model.addAttribute("patient",patient);
        model.addAttribute("prescription",new Disease());
        return "/prescription";
    }
    @RequestMapping("/getPatientById/{id}")
    public String getPatientById(Model model, @PathVariable("id") String id){
        Patient patient = patientService.getPatientById(Long.parseLong(id));

        return "patient";
    }

    @PostMapping("/savePrescription/{id}")
    public String prescription(@PathVariable("id") String id, Disease prescription){
        Patient patient = patientService.getPatientById(Long.parseLong(id));
        prescription.setId(null);
        System.out.println(patient);
        prescription=diseaseService.save(prescription);
        if(patient.getDiseaseList()==null){
            List<Disease> diseaseList=new ArrayList<>();
            diseaseList.add(prescription);
            patient.setDiseaseList(diseaseList);
        }else {
            patient.getDiseaseList().add(prescription);
        }
        patientService.save(patient);
        return home();
    }
    @PostMapping("/searchResult")
        public String getAllPatients(Model model, SearchInputs search){
        model.addAttribute("search", new SearchInputs());
        model.addAttribute("patients", patientService.getPatientsByValue(search));
        return "/searchResult";
        }
    @GetMapping("/addPrescription/{id}")
    public String addNewPrescription(Model model, @PathVariable ("id") String id){
        Patient patient = patientService.getPatientById(Long.parseLong(id));
        model.addAttribute("medicines",patient.getDiseaseList());
        model.addAttribute("patient",patient);
        model.addAttribute("prescription",new Disease());
        return "/prescription";
    }
    @GetMapping(value = "/viewprescription/{id}")
    public String precriptionViewPage( Model model, @PathVariable ("id") String id){
        Patient patient = patientService.getPatientById(Long.parseLong(id));
        model.addAttribute("medicines",patient.getDiseaseList());
        model.addAttribute("patient",patient);
        return "/viewprescription";
    }
}
