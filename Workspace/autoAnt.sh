#!/bin/bash
ant -buildfile AdmiralRadarCommon/build.xml
ant -buildfile AdmiralRadarServer/build.xml
ant -buildfile AdmiralRadarClient/build.xml
ant -buildfile AdmiralRadarTesting/build.xml
