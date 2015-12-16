/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2014, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.process.spatialstatistics;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.geotools.data.Parameter;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.NameImpl;
import org.geotools.process.Process;
import org.geotools.process.spatialstatistics.autocorrelation.JoinCountStatisticsOperation.JoinCountResult;
import org.geotools.process.spatialstatistics.enumeration.ContiguityType;
import org.geotools.util.KVP;
import org.geotools.util.logging.Logging;
import org.opengis.filter.Filter;
import org.opengis.util.InternationalString;

/**
 * JoinCountProcessFactory
 * 
 * @author Minpa Lee, MangoSystem
 * 
 * @source $URL$
 */
public class JoinCountStatisticsProcessFactory extends SpatialStatisticsProcessFactory {
    protected static final Logger LOGGER = Logging
            .getLogger(JoinCountStatisticsProcessFactory.class);

    private static final String PROCESS_NAME = "JoinCount";

    /*
     * JoinCount(SimpleFeatureCollection inputFeatures, Filter trueExpression, ContiguityType contiguityType): JoinCountResult
     */

    public JoinCountStatisticsProcessFactory() {
        super(new NameImpl(NAMESPACE, PROCESS_NAME));
    }

    @Override
    public Process create() {
        return new JoinCountStatisticsProcess(this);
    }

    @Override
    public InternationalString getTitle() {
        return getResource("JoinCount.title");
    }

    @Override
    public InternationalString getDescription() {
        return getResource("JoinCount.description");
    }

    /** inputFeatures */
    public static final Parameter<SimpleFeatureCollection> inputFeatures = new Parameter<SimpleFeatureCollection>(
            "inputFeatures", SimpleFeatureCollection.class,
            getResource("JoinCount.inputFeatures.title"),
            getResource("JoinCount.inputFeatures.description"), true, 1, 1, null, new KVP(
                    Parameter.FEATURE_TYPE, "Polygon"));

    /** trueExpression */
    public static final Parameter<Filter> trueExpression = new Parameter<Filter>("trueExpression",
            Filter.class, getResource("JoinCount.trueExpression.title"),
            getResource("JoinCount.trueExpression.description"), true, 1, 1, null, null);

    /** contiguityType */
    public static final Parameter<ContiguityType> contiguityType = new Parameter<ContiguityType>(
            "contiguityType", ContiguityType.class, getResource("JoinCount.contiguityType.title"),
            getResource("JoinCount.contiguityType.description"), false, 0, 1, ContiguityType.Queen,
            null);

    @Override
    protected Map<String, Parameter<?>> getParameterInfo() {
        HashMap<String, Parameter<?>> parameterInfo = new LinkedHashMap<String, Parameter<?>>();
        parameterInfo.put(inputFeatures.key, inputFeatures);
        parameterInfo.put(trueExpression.key, trueExpression);
        parameterInfo.put(contiguityType.key, contiguityType);
        return parameterInfo;
    }

    /** result */
    public static final Parameter<JoinCountResult> RESULT = new Parameter<JoinCountResult>(
            "result", JoinCountResult.class, getResource("JoinCount.result.title"),
            getResource("JoinCount.result.description"));

    static final Map<String, Parameter<?>> resultInfo = new TreeMap<String, Parameter<?>>();
    static {
        resultInfo.put(RESULT.key, RESULT);
    }

    @Override
    protected Map<String, Parameter<?>> getResultInfo(Map<String, Object> parameters)
            throws IllegalArgumentException {
        return Collections.unmodifiableMap(resultInfo);
    }

}