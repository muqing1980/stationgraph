package com.zte.tl.nm4;

import com.zte.tl.nm4.exception.NoSuchRouteException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.util.collections.Sets;

import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class GraphTest {

    @BeforeClass
    public static void createGraph() {
        String[] input = {"AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7"};
        GraphFactory.getInstance().createAndGet(input);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void distance_should_be_9_when_from_A_to_B_to_C() throws Exception {
        int distance = TravelService.routeDistance("A", "B", "C");
        assertThat(distance, is(9));
    }

    @Test
    public void distance_should_be_5_when_from_A_to_D() throws Exception {
        int distance = TravelService.routeDistance("A", "D");
        assertThat(distance, is(5));
    }

    @Test
    public void distance_should_be_13_when_from_A_to_D_to_C() throws Exception {
        int distance = TravelService.routeDistance("A", "D", "C");
        assertThat(distance, is(13));
    }

    @Test
    public void distance_should_be_22_when_from_A_to_E_to_B_to_C_to_D() throws Exception {
        int distance = TravelService.routeDistance("A", "E", "B", "C", "D");
        assertThat(distance, is(22));
    }

    @Test
    public void should_have_exception_when_from_A_to_E_to_D() throws Exception {
        thrown.expect(NoSuchRouteException.class);
        TravelService.routeDistance("A", "E", "D");
    }

    @Test
    public void should_have_2_routes_when_from_C_to_C_where_max_stops_be_3() throws Exception {
        Set<String> expectSet = Sets.newSet("CDC","CEBC");
        List<String> result = TravelService.queryRoutesByMaxViaStations("C", "C", 3);
        assertThat(result.size(), is(2));
        assertTrue(expectSet.containsAll(result));
    }

    @Test
    public void should_have_3_routes_when_from_A_to_C_where_exact_stops_be_4() throws Exception {
        Set<String> expectSet = Sets.newSet("ABCDC","ADCDC","ADEBC");
        List<String> result = TravelService.queryRoutesByExpectViaStations("A", "C", 4);
        assertThat(result.size(), is(3));
        assertTrue(expectSet.containsAll(result));
    }

    @Test
    public void shortest_distance_should_be_9_when_from_A_to_C() throws Exception {
        int shortest = TravelService.queryShortestRoute("A", "C");
        assertThat(shortest, is(9));
    }

    @Test
    public void shortest_distance_should_be_9_when_from_B_to_B() throws Exception {
        int shortest = TravelService.queryShortestRoute("B", "B");
        assertThat(shortest, is(9));
    }

    @Test
    public void difference_routes_num_should_be_7_when_from_C_to_C_where_max_distance_be_30() throws Exception {
        Set<String> expectSet = Sets.newSet("CDC","CEBC","CEBCDC","CDCEBC","CDEBC","CEBCEBC","CEBCEBCEBC");
        List<String> routes = TravelService.queryRouteSeqByThreshold("C","C",30);
        assertThat(routes.size(),is(7));
        assertTrue(expectSet.containsAll(routes));
    }
}