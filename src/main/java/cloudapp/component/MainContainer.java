package cloudapp.component;

import java.util.Map;

import org.springframework.stereotype.Component;

import cloudapp.entity.Table;

@Component
public class MainContainer {
    private Map<String, Table> tableMap;
}