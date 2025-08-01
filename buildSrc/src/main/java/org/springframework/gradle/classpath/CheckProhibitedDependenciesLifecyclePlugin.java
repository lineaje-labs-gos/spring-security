/*
 * Copyright 2004-present the original author or authors.
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

package org.springframework.gradle.classpath;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Rob Winch
 */
public class CheckProhibitedDependenciesLifecyclePlugin implements Plugin<Project> {
	public static final String CHECK_PROHIBITED_DEPENDENCIES_TASK_NAME = "checkForProhibitedDependencies";

	@Override
	public void apply(Project project) {
		TaskProvider<Task> checkProhibitedDependencies = project.getTasks().register(CheckProhibitedDependenciesLifecyclePlugin.CHECK_PROHIBITED_DEPENDENCIES_TASK_NAME, task -> {
			task.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
			task.setDescription("Checks both the compile/runtime classpath of every SourceSet for prohibited dependencies");
		});
		project.getTasks().named(JavaBasePlugin.CHECK_TASK_NAME, checkTask -> checkTask.dependsOn(checkProhibitedDependencies));
	}
}
