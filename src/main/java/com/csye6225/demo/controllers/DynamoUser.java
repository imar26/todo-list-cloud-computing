package com.csye6225.demo.controllers;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.UUID;

public class DynamoUser {

    public boolean verifyTokenInDynamo(String userName) throws Exception{

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable("csye6225");
        Item item = table.getItem("userName", userName);

        if(item == null){
            return false;
        }
        return true;

    }

    public void addTokenForUser(String userName){
        UUID uuid = UUID.randomUUID();
        Item item = new Item().withPrimaryKey("token", uuid.toString()).withString("userName", userName);
    }
}
