package com.bot.ai;

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

import static dev.langchain4j.store.embedding.filter.MetadataFilterBuilder.metadataKey;

public class TestDemo {

    public static void main(String[] args) {

        EmbeddingStore<TextSegment> embeddingStore = PgVectorEmbeddingStore.builder()
                .host("localhost")
                .port(5432)
                .database("escuela_database")
                .user("escuela_user")
                .password("escuela_password")
                .table("test")
                .dimension(384)
                .build();

        readData(embeddingStore);

    }


    public static void readData(EmbeddingStore<TextSegment> embeddingStore) {

        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();

        Embedding queryEmbedding = embeddingModel.embed("empId with 1001").content();
//        Filter onlyForUser1 = metadataKey("Skills").isEqualTo("Java");
        Filter filter2 = metadataKey("empId").isEqualTo("1001");


        EmbeddingSearchRequest embeddingSearchRequest1 = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
//                .filter(onlyForUser1)
                .filter(filter2)
                .build();

        EmbeddingSearchResult<TextSegment> embeddingSearchResult1 = embeddingStore.search(embeddingSearchRequest1);
        EmbeddingMatch<TextSegment> embeddingMatch1 = embeddingSearchResult1.matches().get(0);
        System.out.println(embeddingMatch1.score());
        System.out.println(embeddingMatch1.embedded().text());

        for(EmbeddingMatch<TextSegment> match: embeddingSearchResult1.matches()){
            System.out.println(match.score());
            System.out.println(match.embedded().text());
        }

    }
}
