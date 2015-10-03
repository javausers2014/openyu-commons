package org.openyu.commons.service.supporter;

import java.util.Properties;

import org.apache.commons.collections.ExtendedProperties;
import org.openyu.commons.service.BaseFactory;
import org.openyu.commons.service.StartCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 工廠類
 * 
 * 1.configLocation
 * 
 * 2.properties
 */
public abstract class BaseFactorySupporter<T> extends BaseServiceSupporter implements BaseFactory<T> {

	private static final long serialVersionUID = -1565481677571797118L;

	private static final transient Logger LOGGER = LoggerFactory.getLogger(BaseFactorySupporter.class);

	private Resource configLocation;

	private Properties properties;

	/**
	 * 改用ExtendedProperties
	 */
	protected ExtendedProperties extendedProperties;

	public BaseFactorySupporter() {
		addServiceCallback(new StartCallbacker());
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * 內部啟動
	 */
	protected class StartCallbacker implements StartCallback {

		@Override
		public void doInAction() throws Exception {
			mergeProperties();
		}
	}

	/**
	 * 合併設定
	 * 
	 * @throws Exception
	 */
	protected void mergeProperties() throws Exception {
		Properties props = new Properties();
		if (this.configLocation != null) {
			PropertiesLoaderUtils.fillProperties(props, this.configLocation);
		}
		if (this.properties != null) {
			props.putAll(this.properties);
			this.properties.clear();
		}
		//
		this.extendedProperties = ExtendedProperties.convertProperties(props);
	}
}
