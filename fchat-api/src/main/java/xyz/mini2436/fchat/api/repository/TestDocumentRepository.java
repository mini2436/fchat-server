package xyz.mini2436.fchat.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import xyz.mini2436.fchat.api.model.po.mongo.TestDocument;

/**
 * mogoDb的扯时dao层
 *
 * @author: mini2436
 * @date: 2021-07-09 09:48
 **/
public interface TestDocumentRepository extends ReactiveMongoRepository<TestDocument,Integer> {

}
