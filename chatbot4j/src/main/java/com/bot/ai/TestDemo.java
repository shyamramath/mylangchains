package com.bot.ai;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

public class TestDemo {

    public static void main(String[] args) {

    }

    public static String main1(String message) {

        EmbeddingStore<TextSegment> embeddingStore = PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("postgres")
                .user("escuela_user")
                .password("escuela_password")
                .table("test")
                .dimension(384)
                .build();

//        embeddingStore.removeAll();
//        loadData(embeddingStore);
//        readData(embeddingStore);
        return readData(embeddingStore,message);
    }

    public static String readData(EmbeddingStore<TextSegment> embeddingStore,String message) {
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
//        Embedding queryEmbedding = embeddingModel.embed("What is the status of the Students with ").content();
        Embedding queryEmbedding = embeddingModel.embed(message).content();
        Filter onlyForUser1 = metadataKey("studentID").isEqualTo("1");
//        Filter filter2 = metadataKey("status").isEqualTo("Failed");/**/


        EmbeddingSearchRequest embeddingSearchRequest1 = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
//                .filter(onlyForUser1)
//                .filter(filter2)
                .build();

        StringBuilder response = new StringBuilder();
        EmbeddingSearchResult<TextSegment> embeddingSearchResult1 = embeddingStore.search(embeddingSearchRequest1);
        for(EmbeddingMatch<TextSegment> match: embeddingSearchResult1.matches()){
            System.out.println(match.score());
//            System.out.println(match.embedded().text());
            response.append(match.embedded().text());
        }

        // Filter for students with grade First Class
        EmbeddingSearchRequest embeddingSearchRequest2 = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("grade").isEqualTo("First Class"))
                .build();

        return response.toString();

    }


    public static void readData(EmbeddingStore<TextSegment> embeddingStore) {
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
//        Embedding queryEmbedding = embeddingModel.embed("What is the status of the Students with ").content();
        Embedding queryEmbedding = embeddingModel.embed("list all students with grade First Class").content();
        Filter onlyForUser1 = metadataKey("studentID").isEqualTo("1");
//        Filter filter2 = metadataKey("status").isEqualTo("Failed");/**/


        EmbeddingSearchRequest embeddingSearchRequest1 = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
//                .filter(onlyForUser1)
//                .filter(filter2)
                .build();

        EmbeddingSearchResult<TextSegment> embeddingSearchResult1 = embeddingStore.search(embeddingSearchRequest1);
        for(EmbeddingMatch<TextSegment> match: embeddingSearchResult1.matches()){
            System.out.println(match.score());
            System.out.println(match.embedded().text());
        }

        // Filter for students with grade First Class
        EmbeddingSearchRequest embeddingSearchRequest2 = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .filter(metadataKey("grade").isEqualTo("First Class"))
                .build();

    }

    /**
     *
     * @param embeddingStore
     */
    public static void loadData(EmbeddingStore<TextSegment> embeddingStore){

        for(Student student:getStudentlist()){

            Map<String,String> studentMetadata = new HashMap<>();
            studentMetadata.put("studentID", student.id());
            studentMetadata.put("Student_name", student.name());
            studentMetadata.put("status", student.status());
            studentMetadata.put("grade", student.grade());

//            String template = "Id: %s, Firstname: %s, Status: %s, Grade: %s";
            String template = "Student %s with studentID %s has %s with Grade %s ";
            template = String.format(template, student.name(), student.id(), student.status(),student.grade() );

            TextSegment studentSegment1 = TextSegment.from(template, new Metadata(studentMetadata));
            EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
            Embedding embedding1 = embeddingModel.embed(studentSegment1).content();
            embeddingStore.add(embedding1, studentSegment1);
        }

    }


    /**
     *
     * @return
     */
    static List<Student> getStudentlist(){
        Student student= new Student("1","Shyam","Passed","First Class");
        Student student2= new Student("2","Ambili","Passed","Second Class");
        Student student3= new Student("3","Ameya","Passed","Third Class");
        Student student4= new Student("4","Dhyam","Passed","Fourth Class");
        Student student5= new Student("5","Jadeja","Failed","Fifth Class");
        Student student6= new Student("4","Lamba","Failed","Fourth Class");
        List<Student> studentlist= new ArrayList<>();
        studentlist.add(student);
        studentlist.add(student2);
        studentlist.add(student3);
        studentlist.add(student4);
        studentlist.add(student5);
        studentlist.add(student6);
        return studentlist;
    }

}
