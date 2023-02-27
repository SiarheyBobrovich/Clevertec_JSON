package ru.clevertec.json.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectParserTest {
    private ObjectParser parser;
    @BeforeEach
    void setUp() {
        parser = new ObjectParser();
    }

    @Test
    void parseJson() {
        String json = "{\"aByte\":39,\"aShort\":10823,\"aChar\":\"僻\",\"anInt\":1300184486,\"aLong\":2753068181461353807,\"aFloat\":1.3448593E38,\"aDouble\":1.3415315526997536E308,\"string\":\"five\",\"id\":4300258749412851719,\"name\":\"SIX\",\"primitives\":[{\"aByte\":69,\"aShort\":2774,\"aChar\":\"༆\",\"anInt\":38956079,\"aLong\":5206220307479642712,\"aFloat\":2.2811853E38,\"aDouble\":1.4755949280453925E308,\"string\":\"second\"},{\"aByte\":61,\"aShort\":31936,\"aChar\":\"쵩\",\"anInt\":717553384,\"aLong\":8514508821139029225,\"aFloat\":3.0919525E38,\"aDouble\":3.6330257625401235E307,\"string\":\"t h i r d\"},{\"aByte\":34,\"aShort\":27667,\"aChar\":\"⥶\",\"anInt\":29477589,\"aLong\":7974906910798020914,\"aFloat\":3.3239824E37,\"aDouble\":9.883719294126998E307,\"string\":\"S-E-V-E-N\"}],\"primitiveList\":[{\"aByte\":124,\"aShort\":19562,\"aChar\":\"曓\",\"anInt\":756938214,\"aLong\":3866253076425425134,\"aFloat\":2.2207308E38,\"aDouble\":2.2065246852665895E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":33,\"aShort\":21942,\"aChar\":\"\uE377\",\"anInt\":1018571981,\"aLong\":3095031631164977922,\"aFloat\":1.3799607E38,\"aDouble\":1.14651422688926E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":25,\"aShort\":9760,\"aChar\":\"\u137E\",\"anInt\":1227026572,\"aLong\":3471992204028563764,\"aFloat\":2.5019598E38,\"aDouble\":1.6726790551590864E308,\"string\":\"SIX\"}],\"primitiveSet\":[{\"aByte\":48,\"aShort\":25259,\"aChar\":\"簝\",\"anInt\":1505969050,\"aLong\":7942861689496338941,\"aFloat\":2.0918928E38,\"aDouble\":1.5416278050121798E308,\"string\":\"first\"},{\"aByte\":79,\"aShort\":29077,\"aChar\":\"巧\",\"anInt\":1012940655,\"aLong\":8871537767886017891,\"aFloat\":3.18586E38,\"aDouble\":8.513929328550093E307,\"string\":\"f o r th\"},{\"aByte\":103,\"aShort\":3618,\"aChar\":\"춫\",\"anInt\":739140582,\"aLong\":187177568256886130,\"aFloat\":1.8881493E38,\"aDouble\":3.1951814392649814E307,\"string\":\"f o r th\"}],\"primitiveMap\":{\"f o r th\":{\"aByte\":127,\"aShort\":13986,\"aChar\":\"歯\",\"anInt\":1507335619,\"aLong\":5752992775444400146,\"aFloat\":3.0745502E38,\"aDouble\":1.7951348114499398E308,\"string\":\"f o r th\"},\"second\":{\"aByte\":35,\"aShort\":20395,\"aChar\":\"诡\",\"anInt\":1743141457,\"aLong\":1453632269590500743,\"aFloat\":1.2000908E38,\"aDouble\":7.413962077033775E307,\"string\":\"second\"}}}";
        List<String> names = List.of(
                "aByte",
                "aShort",
                "aChar",
                "anInt",
                "aLong",
                "aFloat",
                "aDouble",
                "string",
                "id",
                "name",
                "primitives",
                "primitiveList",
                "primitiveSet",
                "primitiveMap"
        );
        Map<String, String> stringStringMap = parser.parseJson(json);
        assertTrue(names.containsAll(stringStringMap.keySet()));
    }
    
    @Test
    void checkParseJsonValue() {
        String json = "{\"aByte\":39,\"aShort\":10823,\"aChar\":\"僻\",\"anInt\":1300184486,\"aLong\":2753068181461353807,\"aFloat\":1.3448593E38,\"aDouble\":1.3415315526997536E308,\"string\":\"five\",\"id\":4300258749412851719,\"name\":\"SIX\",\"primitives\":[{\"aByte\":69,\"aShort\":2774,\"aChar\":\"༆\",\"anInt\":38956079,\"aLong\":5206220307479642712,\"aFloat\":2.2811853E38,\"aDouble\":1.4755949280453925E308,\"string\":\"second\"},{\"aByte\":61,\"aShort\":31936,\"aChar\":\"쵩\",\"anInt\":717553384,\"aLong\":8514508821139029225,\"aFloat\":3.0919525E38,\"aDouble\":3.6330257625401235E307,\"string\":\"t h i r d\"},{\"aByte\":34,\"aShort\":27667,\"aChar\":\"⥶\",\"anInt\":29477589,\"aLong\":7974906910798020914,\"aFloat\":3.3239824E37,\"aDouble\":9.883719294126998E307,\"string\":\"S-E-V-E-N\"}],\"primitiveList\":[{\"aByte\":124,\"aShort\":19562,\"aChar\":\"曓\",\"anInt\":756938214,\"aLong\":3866253076425425134,\"aFloat\":2.2207308E38,\"aDouble\":2.2065246852665895E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":33,\"aShort\":21942,\"aChar\":\"\uE377\",\"anInt\":1018571981,\"aLong\":3095031631164977922,\"aFloat\":1.3799607E38,\"aDouble\":1.14651422688926E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":25,\"aShort\":9760,\"aChar\":\"\u137E\",\"anInt\":1227026572,\"aLong\":3471992204028563764,\"aFloat\":2.5019598E38,\"aDouble\":1.6726790551590864E308,\"string\":\"SIX\"}],\"primitiveSet\":[{\"aByte\":48,\"aShort\":25259,\"aChar\":\"簝\",\"anInt\":1505969050,\"aLong\":7942861689496338941,\"aFloat\":2.0918928E38,\"aDouble\":1.5416278050121798E308,\"string\":\"first\"},{\"aByte\":79,\"aShort\":29077,\"aChar\":\"巧\",\"anInt\":1012940655,\"aLong\":8871537767886017891,\"aFloat\":3.18586E38,\"aDouble\":8.513929328550093E307,\"string\":\"f o r th\"},{\"aByte\":103,\"aShort\":3618,\"aChar\":\"춫\",\"anInt\":739140582,\"aLong\":187177568256886130,\"aFloat\":1.8881493E38,\"aDouble\":3.1951814392649814E307,\"string\":\"f o r th\"}],\"primitiveMap\":{\"f o r th\":{\"aByte\":127,\"aShort\":13986,\"aChar\":\"歯\",\"anInt\":1507335619,\"aLong\":5752992775444400146,\"aFloat\":3.0745502E38,\"aDouble\":1.7951348114499398E308,\"string\":\"f o r th\"},\"second\":{\"aByte\":35,\"aShort\":20395,\"aChar\":\"诡\",\"anInt\":1743141457,\"aLong\":1453632269590500743,\"aFloat\":1.2000908E38,\"aDouble\":7.413962077033775E307,\"string\":\"second\"}}}";
        List<String> names = List.of(
            "39",
            "10823",
            "\"僻\"",
            "1300184486",
            "2753068181461353807",
            "1.3448593E38",
            "1.3415315526997536E308",
            "\"five\"",
            "4300258749412851719",
            "\"SIX\"",
            "[{\"aByte\":69,\"aShort\":2774,\"aChar\":\"༆\",\"anInt\":38956079,\"aLong\":5206220307479642712,\"aFloat\":2.2811853E38,\"aDouble\":1.4755949280453925E308,\"string\":\"second\"},{\"aByte\":61,\"aShort\":31936,\"aChar\":\"쵩\",\"anInt\":717553384,\"aLong\":8514508821139029225,\"aFloat\":3.0919525E38,\"aDouble\":3.6330257625401235E307,\"string\":\"t h i r d\"},{\"aByte\":34,\"aShort\":27667,\"aChar\":\"⥶\",\"anInt\":29477589,\"aLong\":7974906910798020914,\"aFloat\":3.3239824E37,\"aDouble\":9.883719294126998E307,\"string\":\"S-E-V-E-N\"}]",
            "[{\"aByte\":124,\"aShort\":19562,\"aChar\":\"曓\",\"anInt\":756938214,\"aLong\":3866253076425425134,\"aFloat\":2.2207308E38,\"aDouble\":2.2065246852665895E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":33,\"aShort\":21942,\"aChar\":\"\",\"anInt\":1018571981,\"aLong\":3095031631164977922,\"aFloat\":1.3799607E38,\"aDouble\":1.14651422688926E307,\"string\":\"S-E-V-E-N\"},{\"aByte\":25,\"aShort\":9760,\"aChar\":\"፾\",\"anInt\":1227026572,\"aLong\":3471992204028563764,\"aFloat\":2.5019598E38,\"aDouble\":1.6726790551590864E308,\"string\":\"SIX\"}]",
            "[{\"aByte\":48,\"aShort\":25259,\"aChar\":\"簝\",\"anInt\":1505969050,\"aLong\":7942861689496338941,\"aFloat\":2.0918928E38,\"aDouble\":1.5416278050121798E308,\"string\":\"first\"},{\"aByte\":79,\"aShort\":29077,\"aChar\":\"巧\",\"anInt\":1012940655,\"aLong\":8871537767886017891,\"aFloat\":3.18586E38,\"aDouble\":8.513929328550093E307,\"string\":\"f o r th\"},{\"aByte\":103,\"aShort\":3618,\"aChar\":\"춫\",\"anInt\":739140582,\"aLong\":187177568256886130,\"aFloat\":1.8881493E38,\"aDouble\":3.1951814392649814E307,\"string\":\"f o r th\"}]",
            "{\"f o r th\":{\"aByte\":127,\"aShort\":13986,\"aChar\":\"歯\",\"anInt\":1507335619,\"aLong\":5752992775444400146,\"aFloat\":3.0745502E38,\"aDouble\":1.7951348114499398E308,\"string\":\"f o r th\"},\"second\":{\"aByte\":35,\"aShort\":20395,\"aChar\":\"诡\",\"anInt\":1743141457,\"aLong\":1453632269590500743,\"aFloat\":1.2000908E38,\"aDouble\":7.413962077033775E307,\"string\":\"second\"}}"
        );
        Map<String, String> stringStringMap = parser.parseJson(json);
        boolean b = names.containsAll(stringStringMap.values());

        assertTrue(b);
    }
}