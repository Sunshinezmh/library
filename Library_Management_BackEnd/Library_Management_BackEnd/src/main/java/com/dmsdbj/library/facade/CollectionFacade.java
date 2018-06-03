package com.dmsdbj.library.facade;


import com.dmsdbj.itoo.tool.business.ItooResult;
import com.dmsdbj.library.viewmodel.Collection;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-10-11T08:49:46.795+08:00")
public interface CollectionFacade {
     ItooResult addcollection(Collection form);
     ItooResult deleteCollection(Long collectionId);
     ItooResult deleteCollections(Long deleteCollectionIds);
     ItooResult getCollectionsByuserId(String userId);
}
