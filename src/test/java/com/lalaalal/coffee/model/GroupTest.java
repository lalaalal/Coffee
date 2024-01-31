package com.lalaalal.coffee.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.model.menu.Group;
import org.junit.jupiter.api.Test;

class GroupTest {
    @Test
    void test() throws JsonProcessingException {
        Group group = new Group("a", 0);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(group));
    }

}