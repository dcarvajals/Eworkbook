/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apis;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author CleanCode
 */
@javax.ws.rs.ApplicationPath("webapis")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(Apis.ClassroomApis.class);
        resources.add(Apis.EvaluationApis.class);
        resources.add(Apis.Evaluation_categoriesApis.class);
        resources.add(Apis.Evaluation_questionsApis.class);
        resources.add(Apis.Evaluation_responsesApis.class);
        resources.add(Apis.GeneralApis.class);
        resources.add(Apis.PeriodApis.class);
        resources.add(Apis.PersonApis.class);
        resources.add(Apis.QuestionsApis.class);
        resources.add(Apis.Questions_groupApis.class);
        resources.add(Apis.ResponsesApis.class);
    }

}
