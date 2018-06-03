/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dmsdbj.library.controller;

//import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.dmsdbj.library.app.metrics.MetricsConfigurer;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

;

/**
 *
 * @author 10283_000
 */
@javax.ws.rs.ApplicationPath("resources")
public class ApplicationConfig extends Application {

    @Inject
    private MetricsConfigurer metricsConfigurer;

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
        addRestResourceClasses(resources);
        return resources;
    }


    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<>();
        instances.add(new JacksonJsonProvider());
//        instances.add(new InstrumentedResourceMethodApplicationListener(metricsConfigurer.getMetricRegistry()));
        return instances;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
//        resources.add(com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener.class);
        resources.add(com.dmsdbj.library.app.metrics.DiagnosticFilter.class);
        resources.add(com.dmsdbj.library.app.security.SecurityUtils.class);
        resources.add(com.dmsdbj.library.app.security.jwt.JWTAuthenticationFilter.class);
        resources.add(com.dmsdbj.library.controller.AccountController.class);
        resources.add(com.dmsdbj.library.controller.BookController.class);
        resources.add(com.dmsdbj.library.controller.BookTypeController.class);
        resources.add(com.dmsdbj.library.controller.BorrowingController.class);
        resources.add(com.dmsdbj.library.controller.CollectionController.class);
        resources.add(com.dmsdbj.library.controller.CommentController.class);
        resources.add(com.dmsdbj.library.controller.DonateController.class);
        resources.add(com.dmsdbj.library.controller.FriendsController.class);
        resources.add(com.dmsdbj.library.controller.HomeController.class);
        resources.add(com.dmsdbj.library.controller.LoginController.class);
        resources.add(com.dmsdbj.library.controller.LogsResource.class);
        resources.add(com.dmsdbj.library.controller.MessageController.class);
        resources.add(com.dmsdbj.library.controller.NotesController.class);
        resources.add(com.dmsdbj.library.controller.OpinionController.class);
        resources.add(com.dmsdbj.library.controller.OverviewController.class);
        resources.add(com.dmsdbj.library.controller.ReserveController.class);
        resources.add(com.dmsdbj.library.controller.RulesController.class);
        resources.add(com.dmsdbj.library.controller.StudentController.class);
        resources.add(com.dmsdbj.library.controller.TSettingController.class);
        resources.add(com.dmsdbj.library.controller.TUserController.class);
        resources.add(com.dmsdbj.library.controller.TeacherController.class);
        resources.add(com.dmsdbj.library.controller.UserJWTController.class);
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonParseExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }
    
}
