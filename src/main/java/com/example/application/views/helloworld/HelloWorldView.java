package com.example.application.views.helloworld;

import com.example.application.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.ChartOptions;
import com.vaadin.flow.component.charts.model.AxisType;
import com.vaadin.flow.component.charts.model.Bottom;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ColorAxis;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem3d;
import com.vaadin.flow.component.charts.model.DataSeriesItemTimeline;
import com.vaadin.flow.component.charts.model.Frame;
import com.vaadin.flow.component.charts.model.MarkerSymbolEnum;
import com.vaadin.flow.component.charts.model.Options3d;
import com.vaadin.flow.component.charts.model.PlotOptionsScatter;
import com.vaadin.flow.component.charts.model.PlotOptionsTimeline;
import com.vaadin.flow.component.charts.model.PlotOptionsTreemap;
import com.vaadin.flow.component.charts.model.TreeMapLayoutAlgorithm;
import com.vaadin.flow.component.charts.model.TreeSeries;
import com.vaadin.flow.component.charts.model.TreeSeriesItem;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.charts.model.ZAxis;
import com.vaadin.flow.component.charts.model.style.SolidColor;
import com.vaadin.flow.component.charts.themes.LumoLightTheme;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("./views/helloworld/hello-world-view.css")
public class HelloWorldView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;
    protected Chart getTreeMapChart() {
        Chart chart = new Chart(ChartType.TREEMAP);

        ColorAxis colorAxis = new ColorAxis();
        colorAxis.setMinColor(new SolidColor("#FFFFFF"));
        colorAxis.setMaxColor(new SolidColor("#7BB5EF"));
        chart.getConfiguration().addColorAxis(colorAxis);

        PlotOptionsTreemap plotOptions = new PlotOptionsTreemap();
        plotOptions.setLayoutAlgorithm(TreeMapLayoutAlgorithm.SQUARIFIED);
        TreeSeries series = createSeries();
        series.setPlotOptions(plotOptions);
        chart.getConfiguration().addSeries(series);
        chart.getConfiguration().setTitle("Vaadin Charts Treemap");
        return chart;
    }

    private TreeSeries createSeries() {
        List<TreeSeriesItem> items = new ArrayList<TreeSeriesItem>();

        items.add(new TreeSeriesItem("A", 6));
        items.add(new TreeSeriesItem("B", 6));
        items.add(new TreeSeriesItem("C", 4));
        items.add(new TreeSeriesItem("D", 3));
        items.add(new TreeSeriesItem("E", 2));
        items.add(new TreeSeriesItem("F", 2));
        items.add(new TreeSeriesItem("G", 1));

        for (int i = 1; i <= items.size(); i++) {
            items.get(i - 1).setColorValue(i);
        }

        TreeSeries series = new TreeSeries();

        series.setData(items);

        return series;
    }
    private Chart createScatterChart() {
        final Chart scatterChart = new Chart(ChartType.SCATTER);
        scatterChart.setId("chart");
        scatterChart.getConfiguration().disableCredits();
        scatterChart.getConfiguration().setTitle("Points of a sphere");
        PlotOptionsScatter scatterOptions = new PlotOptionsScatter();
        scatterOptions.setAnimation(false);
        scatterOptions.setPointStart(1);

        DataSeries higherX = new DataSeries();
        higherX.setName("Higher X");
        DataSeries higherY = new DataSeries();
        higherY.setName("Higher Y");
        DataSeries higherZ = new DataSeries();
        higherZ.setName("Higher Z");

        fillSeries(higherX, higherY, higherZ);

        scatterChart.getConfiguration().addSeries(higherX);
        scatterChart.getConfiguration().addSeries(higherY);
        scatterChart.getConfiguration().addSeries(higherZ);

        XAxis x = scatterChart.getConfiguration().getxAxis();
        x.setGridLineWidth(1);
        x.setExtremes(-3, 3);

        LumoLightTheme lumoLightTheme = new LumoLightTheme();
        if (lumoLightTheme.getxAxis().getGridLineColor() != null) {
            x.setGridLineColor(lumoLightTheme.getxAxis().getGridLineColor());
        }

        YAxis y = scatterChart.getConfiguration().getyAxis();
        y.setExtremes(-1, 1);

        ZAxis z = scatterChart.getConfiguration().getzAxis();
        z.setMin(-1);
        z.setMax(1);

        Options3d options3d = new Options3d();
        options3d.setEnabled(true);
        options3d.setAlpha(10);
        options3d.setBeta(30);
        options3d.setDepth(80);
        options3d.setViewDistance(40);

        Frame frame = new Frame();
        Bottom bottom = new Bottom();
        bottom.setSize(1);
        frame.setBottom(bottom);
        options3d.setFrame(frame);
        scatterChart.getConfiguration().getChart().setOptions3d(options3d);

        scatterChart.drawChart();
        return scatterChart;

    }

    private void fillSeries(DataSeries higherX, DataSeries higherY,
                            DataSeries higherZ) {
        Random random = new Random(7);
        for (int i = 0; i < 300; i++) {
            double lng = random.nextDouble() * 2 * Math.PI;
            double lat = random.nextDouble() * Math.PI - Math.PI / 2;
            double x = Math.cos(lat) * Math.sin(lng);
            double y = Math.sin(lat);
            double z = Math.cos(lng) * Math.cos(lat);

            DataSeriesItem3d point = new DataSeriesItem3d(x, y, z);
            if (x > y && x > z) {
                higherX.add(point);
            } else if (y > x && y > z) {
                higherY.add(point);
            } else {
                higherZ.add(point);
            }
        }

    }


    public HelloWorldView() {
        Chart chart = getTimelineChart();
        Chart chart2 = createScatterChart();
        Chart chart3 = getTreeMapChart();
        add(chart, chart2, chart3);
    }

    private Chart getTimelineChart() {
        Chart chart = new Chart(ChartType.TIMELINE);
        // Modify the default configuration
        Configuration conf = chart.getConfiguration();
        conf.setTitle("Timeline of Space Exploration");
        conf.setSubTitle(
                "Info source: <a href=\"https://en.wikipedia.org/wiki/Timeline_of_space_exploration\">www.wikipedia.org</a>");
        conf.getTooltip().setEnabled(true);

        // Add data
        DataSeries series = new DataSeries();
        series.add(new DataSeriesItemTimeline(getInstant(1951, 6, 22),
                "First dogs in space", "First dogs in space",
                "Dezik and Tsygan were the first dogs to make a sub-orbital flight on 22 July 1951. Both dogs were recovered unharmed after travelling to a maximum altitude of 110 km."));
        series.add(new DataSeriesItemTimeline(getInstant(1957, 10, 4),
                "First artificial satellite", "First artificial satellite",
                "Sputnik 1 was the first artificial Earth satellite. The Soviet Union launched it into an elliptical low Earth orbit on 4 October 1957, orbiting for three weeks before its batteries died, then silently for two more months before falling back into the atmosphere."));
        series.add(new DataSeriesItemTimeline(getInstant(1959, 1, 4),
                "First artificial satellite to reach the Moon",
                "First artificial satellite to reach the Moon",
                "Luna 1 was the first artificial satellite to reach the Moon vicinity and first artificial satellite in heliocentric orbit."));
        series.add(new DataSeriesItemTimeline(getInstant(1961, 4, 12),
                "First human spaceflight", "First human spaceflight",
                "Yuri Gagarin was a Soviet pilot and cosmonaut. He became the first human to journey into outer space when his Vostok spacecraft completed one orbit of the Earth on 12 April 1961."));
        series.add(new DataSeriesItemTimeline(getInstant(1966, 2, 3),
                "First soft landing on the Moon",
                "First soft landing on the Moon",
                "Yuri Gagarin was a Soviet pilot and cosmonaut. He became the first human to journey into outer space when his Vostok spacecraft completed one orbit of the Earth on 12 April 1961."));
        series.add(new DataSeriesItemTimeline(getInstant(1969, 7, 20),
                "First human on the Moon", "First human on the Moon",
                "Apollo 11 was the spaceflight that landed the first two people on the Moon. Commander Neil Armstrong and lunar module pilot Buzz Aldrin, both American, landed the Apollo Lunar Module Eagle on July 20, 1969, at 20:17 UTC."));
        series.add(new DataSeriesItemTimeline(getInstant(1971, 4, 19),
                "First space station", "First space station",
                "Salyute 1 was the first space station of any kind, launched into low Earth orbit by the Soviet Union on April 19, 1971. The Salyut program followed this with five more successful launches out of seven more stations."));
        series.add(new DataSeriesItemTimeline(getInstant(1971, 12, 2),
                "First soft Mars landing", "First soft Mars landing",
                "Mars 3 was an unmanned space probe of the Soviet Mars program which spanned the years between 1960 and 1973. Mars 3 was launched May 28, 1971, nine days after its twin spacecraft Mars 2. The probes were identical robotic spacecraft launched by Proton-K rockets with a Blok D upper stage, each consisting of an orbiter and an attached lander."));
        series.add(new DataSeriesItemTimeline(getInstant(1976, 4, 17),
                "Closest flyby of the Sun", "Closest flyby of the Sun",
                "Helios-A and Helios-B (also known as Helios 1 and Helios 2) are a pair of probes launched into heliocentric orbit for the purpose of studying solar processes. A joint venture of West Germany's space agency DFVLR (70 percent share) and NASA (30 percent), the probes were launched from Cape Canaveral Air Force Station, Florida."));
        series.add(new DataSeriesItemTimeline(getInstant(1978, 12, 4),
                "First orbital exploration of Venus",
                "First orbital exploration of Venus",
                "The Pioneer Venus Orbiter entered orbit around Venus on December 4, 1978, and performed observations to characterize the atmosphere and surface of Venus. It continued to transmit data until October 1992."));
        series.add(new DataSeriesItemTimeline(getInstant(1986, 2, 19),
                "First inhabited space station",
                "First inhabited space station",
                "was a space station that operated in low Earth orbit from 1986 to 2001, operated by the Soviet Union and later by Russia. Mir was the first modular space station and was assembled in orbit from 1986 to 1996. It had a greater mass than any previous spacecraft."));
        series.add(new DataSeriesItemTimeline(getInstant(1989, 8, 8),
                "First astrometric satellite", "First astrometric satellite",
                "Hipparcos was a scientific satellite of the European Space Agency (ESA), launched in 1989 and operated until 1993. It was the first space experiment devoted to precision astrometry, the accurate measurement of the positions of celestial objects on the sky."));
        series.add(new DataSeriesItemTimeline(getInstant(1998, 11, 20),
                "First multinational space station",
                "First multinational space station",
                "The International Space Station (ISS) is a space station, or a habitable artificial satellite, in low Earth orbit. Its first component was launched into orbit in 1998, with the first long-term residents arriving in November 2000.[7] It has been inhabited continuously since that date."));

        PlotOptionsTimeline options = new PlotOptionsTimeline();
        options.getMarker().setSymbol(MarkerSymbolEnum.CIRCLE);
        DataLabels labels = options.getDataLabels();
        labels.setAllowOverlap(false);
        labels.setFormat(
                "<span style=\"color:{point.color}\">● </span><span style=\"font-weight: bold;\" > {point.x:%d %b %Y}</span><br/>{point.label}");
        series.setPlotOptions(options);
        conf.addSeries(series);

        // Configure the axes
        conf.getxAxis().setVisible(false);
        conf.getxAxis().setType(AxisType.DATETIME);
        conf.getyAxis().setVisible(false);
        return chart;
    }

    // Helper method to create an Instant from year, month and day
    private Instant getInstant(int year, int month, int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth).atStartOfDay().toInstant(ZoneOffset.UTC);
    }

}
