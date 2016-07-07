package com.zte.tl.nm4;

import com.zte.tl.nm4.exception.NoSuchRouteException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.internal.util.collections.Sets;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class GraphTest {

    @BeforeClass
    public static void createGraph() {
        List<String> input = Arrays.asList("AB5", "BC4", "CD8", "DC8", "DE6", "AD5", "CE2", "EB3", "AE7");
        GraphFactory.getInstance().create(input);
    }

    private TravelService service = TravelService.getInstance();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void distance_should_be_9_when_from_A_to_C_via_D() throws Exception {
        Condition condition = new Condition.Builder("A", "C").via("B").build();
        int distance = service.calculateDistance(condition);
        //assert
        assertThat(distance, is(9));
    }

    @Test
    public void distance_should_be_5_when_from_A_to_D() throws Exception {
        Condition condition = new Condition.Builder("A", "D").build();
        int distance = service.calculateDistance(condition);
        //assert
        assertThat(distance, is(5));
    }

    @Test
    public void distance_should_be_13_when_from_A_to_C_via_D() throws Exception {
        Condition condition = new Condition.Builder("A", "C").via("D").build();
        int distance = service.calculateDistance(condition);
        //assert
        assertThat(distance, is(13));
    }

    @Test
    public void distance_should_be_22_when_from_A_to_D_via_E_via_B_via_C() throws Exception {
        Condition condition = new Condition.Builder("A", "D").via("E").via("B").via("C").build();
        int distance = service.calculateDistance(condition);
        //assert
        assertThat(distance, is(22));
    }

    @Test
    public void should_have_exception_when_from_A_to_D_via_E() throws Exception {
        Condition condition = new Condition.Builder("A", "D").via("E").build();
        thrown.expect(NoSuchRouteException.class);
        service.calculateDistance(condition);
    }

    @Test
    public void should_have_2_routes_when_from_C_to_C_where_max_via_stations_be_3() throws Exception {
        Condition condition = new Condition.Builder("C", "C").maxViaStations(3).build();
        List<String> result = service.queryRoutesByMaxViaStations(condition);
        //expect
        Set<String> expectSet = Sets.newSet("CDC", "CEBC");
        //assert
        assertThat(result.size(), is(2));
        assertTrue(expectSet.containsAll(result));
    }

    @Test
    public void should_have_3_routes_when_from_A_to_C_where_expect_via_stations_be_4() throws Exception {
        Condition condition = new Condition.Builder("A", "C").expectViaStations(4).build();
        List<String> result = service.queryRoutesByExpectViaStations(condition);
        //expect
        Set<String> expectSet = Sets.newSet("ABCDC", "ADCDC", "ADEBC");
        //assert
        assertThat(result.size(), is(3));
        assertTrue(expectSet.containsAll(result));
    }

    @Test
    public void shortest_distance_should_be_9_when_from_A_to_C() throws Exception {
        Condition condition = new Condition.Builder("A", "C").build();
        int shortest = service.calculateShortestDistance(condition);
        //assert
        assertThat(shortest, is(9));
    }

    @Test
    public void shortest_distance_should_be_9_when_from_B_to_B() throws Exception {
        Condition condition = new Condition.Builder("B", "B").build();
        int shortest = service.calculateShortestDistance(condition);
        //assert
        assertThat(shortest, is(9));
    }

    @Test
    public void difference_route_num_should_be_7_when_from_C_to_C_where_distance_less_then_30() throws Exception {
        Condition condition = new Condition.Builder("C", "C").distanceUpperLimit(30).build();
        List<String> routes = service.queryRouteSeqByThreshold(condition);
        //expect
        Set<String> expectSet = Sets.newSet("CDC", "CEBC", "CEBCDC", "CDCEBC", "CDEBC", "CEBCEBC", "CEBCEBCEBC");
        //assert
        assertThat(routes.size(), is(7));
        assertTrue(expectSet.containsAll(routes));
    }
}