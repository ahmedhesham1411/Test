package com.uriallab.haat.haat.DataModels;

import java.util.List;

public class OverviewPointModel {

    private List<RoutesEntity> routes;
    private List<Geocoded_waypointsEntity> geocoded_waypoints;
    private String status;

    public void setRoutes(List<RoutesEntity> routes) {
        this.routes = routes;
    }

    public void setGeocoded_waypoints(List<Geocoded_waypointsEntity> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RoutesEntity> getRoutes() {
        return routes;
    }

    public List<Geocoded_waypointsEntity> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public String getStatus() {
        return status;
    }

    public class RoutesEntity {

        private String summary;
        private String copyrights;
        private List<LegsEntity> legs;
        private List<?> warnings;
        private BoundsEntity bounds;
        private Overview_polylineEntity overview_polyline;
        private List<?> waypoint_order;

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public void setLegs(List<LegsEntity> legs) {
            this.legs = legs;
        }

        public void setWarnings(List<?> warnings) {
            this.warnings = warnings;
        }

        public void setBounds(BoundsEntity bounds) {
            this.bounds = bounds;
        }

        public void setOverview_polyline(Overview_polylineEntity overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public void setWaypoint_order(List<?> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        public String getSummary() {
            return summary;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public List<LegsEntity> getLegs() {
            return legs;
        }

        public List<?> getWarnings() {
            return warnings;
        }

        public BoundsEntity getBounds() {
            return bounds;
        }

        public Overview_polylineEntity getOverview_polyline() {
            return overview_polyline;
        }

        public List<?> getWaypoint_order() {
            return waypoint_order;
        }

        public class LegsEntity {

            private DurationEntity duration;
            private Start_locationEntity start_location;
            private DistanceEntity distance;
            private String start_address;
            private End_locationEntity end_location;
            private String end_address;
            private List<?> via_waypoint;
            private List<StepsEntity> steps;
            private List<?> traffic_speed_entry;

            public void setDuration(DurationEntity duration) {
                this.duration = duration;
            }

            public void setStart_location(Start_locationEntity start_location) {
                this.start_location = start_location;
            }

            public void setDistance(DistanceEntity distance) {
                this.distance = distance;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public void setEnd_location(End_locationEntity end_location) {
                this.end_location = end_location;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }

            public void setVia_waypoint(List<?> via_waypoint) {
                this.via_waypoint = via_waypoint;
            }

            public void setSteps(List<StepsEntity> steps) {
                this.steps = steps;
            }

            public void setTraffic_speed_entry(List<?> traffic_speed_entry) {
                this.traffic_speed_entry = traffic_speed_entry;
            }

            public DurationEntity getDuration() {
                return duration;
            }

            public Start_locationEntity getStart_location() {
                return start_location;
            }

            public DistanceEntity getDistance() {
                return distance;
            }

            public String getStart_address() {
                return start_address;
            }

            public End_locationEntity getEnd_location() {
                return end_location;
            }

            public String getEnd_address() {
                return end_address;
            }

            public List<?> getVia_waypoint() {
                return via_waypoint;
            }

            public List<StepsEntity> getSteps() {
                return steps;
            }

            public List<?> getTraffic_speed_entry() {
                return traffic_speed_entry;
            }

            public class DurationEntity {

                private String text;
                private int value;

                public void setText(String text) {
                    this.text = text;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getText() {
                    return text;
                }

                public int getValue() {
                    return value;
                }
            }

            public class Start_locationEntity {

                private double lng;
                private double lat;

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public double getLat() {
                    return lat;
                }
            }

            public class DistanceEntity {

                private String text;
                private int value;

                public void setText(String text) {
                    this.text = text;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getText() {
                    return text;
                }

                public int getValue() {
                    return value;
                }
            }

            public class End_locationEntity {

                private double lng;
                private double lat;

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public double getLat() {
                    return lat;
                }
            }

            public class StepsEntity {

                private DurationEntity duration;
                private Start_locationEntity start_location;
                private DistanceEntity distance;
                private String travel_mode;
                private String html_instructions;
                private End_locationEntity end_location;
                private PolylineEntity polyline;

                public void setDuration(DurationEntity duration) {
                    this.duration = duration;
                }

                public void setStart_location(Start_locationEntity start_location) {
                    this.start_location = start_location;
                }

                public void setDistance(DistanceEntity distance) {
                    this.distance = distance;
                }

                public void setTravel_mode(String travel_mode) {
                    this.travel_mode = travel_mode;
                }

                public void setHtml_instructions(String html_instructions) {
                    this.html_instructions = html_instructions;
                }

                public void setEnd_location(End_locationEntity end_location) {
                    this.end_location = end_location;
                }

                public void setPolyline(PolylineEntity polyline) {
                    this.polyline = polyline;
                }

                public DurationEntity getDuration() {
                    return duration;
                }

                public Start_locationEntity getStart_location() {
                    return start_location;
                }

                public DistanceEntity getDistance() {
                    return distance;
                }

                public String getTravel_mode() {
                    return travel_mode;
                }

                public String getHtml_instructions() {
                    return html_instructions;
                }

                public End_locationEntity getEnd_location() {
                    return end_location;
                }

                public PolylineEntity getPolyline() {
                    return polyline;
                }

                public class DurationEntity {

                    private String text;
                    private int value;

                    public void setText(String text) {
                        this.text = text;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getText() {
                        return text;
                    }

                    public int getValue() {
                        return value;
                    }
                }

                public class Start_locationEntity {

                    private double lng;
                    private double lat;

                    public void setLng(double lng) {
                        this.lng = lng;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public double getLat() {
                        return lat;
                    }
                }

                public class DistanceEntity {

                    private String text;
                    private int value;

                    public void setText(String text) {
                        this.text = text;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getText() {
                        return text;
                    }

                    public int getValue() {
                        return value;
                    }
                }

                public class End_locationEntity {

                    private double lng;
                    private double lat;

                    public void setLng(double lng) {
                        this.lng = lng;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public double getLat() {
                        return lat;
                    }
                }

                public class PolylineEntity {

                    private String points;

                    public void setPoints(String points) {
                        this.points = points;
                    }

                    public String getPoints() {
                        return points;
                    }
                }
            }
        }

        public class BoundsEntity {

            private SouthwestEntity southwest;
            private NortheastEntity northeast;

            public void setSouthwest(SouthwestEntity southwest) {
                this.southwest = southwest;
            }

            public void setNortheast(NortheastEntity northeast) {
                this.northeast = northeast;
            }

            public SouthwestEntity getSouthwest() {
                return southwest;
            }

            public NortheastEntity getNortheast() {
                return northeast;
            }

            public class SouthwestEntity {

                private double lng;
                private double lat;

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public double getLat() {
                    return lat;
                }
            }

            public class NortheastEntity {

                private double lng;
                private double lat;

                public void setLng(double lng) {
                    this.lng = lng;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public double getLat() {
                    return lat;
                }
            }
        }

        public class Overview_polylineEntity {

            private String points;

            public void setPoints(String points) {
                this.points = points;
            }

            public String getPoints() {
                return points;
            }
        }
    }

    public class Geocoded_waypointsEntity {

        private List<String> types;
        private String geocoder_status;
        private String place_id;

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public String getGeocoder_status() {
            return geocoder_status;
        }

        public String getPlace_id() {
            return place_id;
        }
    }
}