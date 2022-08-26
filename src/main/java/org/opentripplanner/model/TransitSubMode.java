package org.opentripplanner.model;

public enum TransitSubMode {
    UNKNOWN("unknown"),

    INTERNATIONALFLIGHT("internationalFlight"),

    DOMESTICFLIGHT("domesticFlight"),

    INTERCONTINENTALFLIGHT("intercontinentalFlight"),

    DOMESTICSCHEDULEDFLIGHT("domesticScheduledFlight"),

    SHUTTLEFLIGHT("shuttleFlight"),

    INTERCONTINENTALCHARTERFLIGHT("intercontinentalCharterFlight"),

    INTERNATIONALCHARTERFLIGHT("internationalCharterFlight"),

    ROUNDTRIPCHARTERFLIGHT("roundTripCharterFlight"),

    SIGHTSEEINGFLIGHT("sightseeingFlight"),

    HELICOPTERSERVICE("helicopterService"),

    DOMESTICCHARTERFLIGHT("domesticCharterFlight"),

    SCHENGENAREAFLIGHT("SchengenAreaFlight"),

    AIRSHIPSERVICE("airshipService"),

    SHORTHAULINTERNATIONALFLIGHT("shortHaulInternationalFlight"),

    CANALBARGE("canalBarge"),

    LOCALBUS("localBus"),

    REGIONALBUS("regionalBus"),

    EXPRESSBUS("expressBus"),

    NIGHTBUS("nightBus"),

    POSTBUS("postBus"),

    SPECIALNEEDSBUS("specialNeedsBus"),

    MOBILITYBUS("mobilityBus"),

    MOBILITYBUSFORREGISTEREDDISABLED("mobilityBusForRegisteredDisabled"),

    SIGHTSEEINGBUS("sightseeingBus"),

    SHUTTLEBUS("shuttleBus"),

    HIGHFREQUENCYBUS("highFrequencyBus"),

    DEDICATEDLANEBUS("dedicatedLaneBus"),

    SCHOOLBUS("schoolBus"),

    SCHOOLANDPUBLICSERVICEBUS("schoolAndPublicServiceBus"),

    RAILREPLACEMENTBUS("railReplacementBus"),

    DEMANDANDRESPONSEBUS("demandAndResponseBus"),

    AIRPORTLINKBUS("airportLinkBus"),

    INTERNATIONALCOACH("internationalCoach"),

    NATIONALCOACH("nationalCoach"),

    SHUTTLECOACH("shuttleCoach"),

    REGIONALCOACH("regionalCoach"),

    SPECIALCOACH("specialCoach"),

    SCHOOLCOACH("schoolCoach"),

    SIGHTSEEINGCOACH("sightseeingCoach"),

    TOURISTCOACH("touristCoach"),

    COMMUTERCOACH("commuterCoach"),

    FUNICULAR("funicular"),

    STREETCABLECAR("streetCableCar"),

    ALLFUNICULARSERVICES("allFunicularServices"),

    UNDEFINEDFUNICULAR("undefinedFunicular"),

    METRO("metro"),

    TUBE("tube"),

    URBANRAILWAY("urbanRailway"),

    CITYTRAM("cityTram"),

    LOCALTRAM("localTram"),

    REGIONALTRAM("regionalTram"),

    SIGHTSEEINGTRAM("sightseeingTram"),

    SHUTTLETRAM("shuttleTram"),

    TRAINTRAM("trainTram"),

    TELECABIN("telecabin"),

    CABLECAR("cableCar"),

    LIFT("lift"),

    CHAIRLIFT("chairLift"),

    DRAGLIFT("dragLift"),

    TELECABINLINK("telecabinLink"),

    LOCAL("local"),

    HIGHSPEEDRAIL("highSpeedRail"),

    SUBURBANRAILWAY("suburbanRailway"),

    REGIONALRAIL("regionalRail"),

    INTERREGIONALRAIL("interregionalRail"),

    LONGDISTANCE("longDistance"),

    INTERNATIONAL("international"),

    SLEEPERRAILSERVICE("sleeperRailService"),

    NIGHTRAIL("nightRail"),

    CARTRANSPORTRAILSERVICE("carTransportRailService"),

    TOURISTRAILWAY("touristRailway"),

    AIRPORTLINKRAIL("airportLinkRail"),

    RAILSHUTTLE("railShuttle"),

    REPLACEMENTRAILSERVICE("replacementRailService"),

    SPECIALTRAIN("specialTrain"),

    CROSSCOUNTRYRAIL("crossCountryRail"),

    RACKANDPINIONRAILWAY("rackAndPinionRailway"),

    INTERNATIONALCARFERRY("internationalCarFerry"),

    NATIONALCARFERRY("nationalCarFerry"),

    REGIONALCARFERRY("regionalCarFerry"),

    LOCALCARFERRY("localCarFerry"),

    INTERNATIONALPASSENGERFERRY("internationalPassengerFerry"),

    NATIONALPASSENGERFERRY("nationalPassengerFerry"),

    REGIONALPASSENGERFERRY("regionalPassengerFerry"),

    LOCALPASSENGERFERRY("localPassengerFerry"),

    POSTBOAT("postBoat"),

    TRAINFERRY("trainFerry"),

    ROADFERRYLINK("roadFerryLink"),

    AIRPORTBOATLINK("airportBoatLink"),

    HIGHSPEEDVEHICLESERVICE("highSpeedVehicleService"),

    HIGHSPEEDPASSENGERSERVICE("highSpeedPassengerService"),

    SIGHTSEEINGSERVICE("sightseeingService"),

    SCHOOLBOAT("schoolBoat"),

    CABLEFERRY("cableFerry"),

    RIVERBUS("riverBus"),

    SCHEDULEDFERRY("scheduledFerry"),

    SHUTTLEFERRYSERVICE("shuttleFerryService"),

    COMMUNALTAXI("communalTaxi"),

    CHARTERTAXI("charterTaxi"),

    WATERTAXI("waterTaxi"),

    RAILTAXI("railTaxi"),

    BIKETAXI("bikeTaxi"),

    BLACKCAB("blackCab"),

    MINICAB("miniCab"),

    ALLTAXISERVICES("allTaxiServices"),

    HIRECAR("hireCar"),

    HIREVAN("hireVan"),

    HIREMOTORBIKE("hireMotorbike"),

    HIRECYCLE("hireCycle"),

    ALLHIREVEHICLES("allHireVehicles");

    private final String rawValue;

    TransitSubMode(String rawValue) {
        this.rawValue = rawValue;
    }

    public String rawValue() {
        return rawValue;
    }

    public static TransitSubMode safeValueOf(String rawValue) {
        for (TransitSubMode enumValue : values()) {
            if (enumValue.rawValue.equalsIgnoreCase(rawValue)) {
                return enumValue;
            }
        }
        return TransitSubMode.UNKNOWN;
    }
}