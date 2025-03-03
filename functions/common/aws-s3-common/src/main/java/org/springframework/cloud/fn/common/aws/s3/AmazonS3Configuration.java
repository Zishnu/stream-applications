/*
 * Copyright 2016-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.fn.common.aws.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.awspring.cloud.autoconfigure.context.ContextCredentialsAutoConfiguration;
import io.awspring.cloud.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import io.awspring.cloud.context.annotation.ConditionalOnMissingAmazonClient;
import io.awspring.cloud.context.config.annotation.ContextDefaultConfigurationRegistrar;
import io.awspring.cloud.core.region.RegionProvider;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Artem Bilan
 */
@AutoConfiguration
@ConditionalOnMissingAmazonClient(AmazonS3.class)
@Import({
		ContextCredentialsAutoConfiguration.class,
		ContextDefaultConfigurationRegistrar.class,
		ContextRegionProviderAutoConfiguration.class
})
public class AmazonS3Configuration {

	@Bean
	@ConditionalOnMissingBean
	public AmazonS3 amazonS3(AWSCredentialsProvider awsCredentialsProvider, RegionProvider regionProvider) {
		return AmazonS3ClientBuilder.standard()
				.withCredentials(awsCredentialsProvider)
				.withRegion(regionProvider.getRegion().getName())
				.build();
	}

}
