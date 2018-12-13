package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        model.addAttribute("title", "Job Detail");
        model.addAttribute("job", jobData.findById(id));


        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        model.addAttribute("title","Add Job");
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()){
            return "new-job";
        }

        Job job = new Job();


        job.setName(jobForm.getName());
        //job = new Job(jobForm.getName(),);
        job.setEmployer(
                jobData.getEmployers().findById(jobForm.getEmployerId())
        );
        job.setLocation(
                jobData.getLocations().findById(jobForm.getLocationId())
        );
        job.setCoreCompetency(
                jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId())
        );
        job.setPositionType(
                jobData.getPositionTypes().findById(jobForm.getPositionTypeId())
        );

        jobData.add(job);
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        model.addAttribute("job",job);

        return "redirect:?id=" + job.getId();

    }
}
