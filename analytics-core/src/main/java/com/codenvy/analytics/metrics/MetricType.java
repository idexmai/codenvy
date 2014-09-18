/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 *  [2012] - [2014] Codenvy, S.A.
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package com.codenvy.analytics.metrics;

/**
 * Predefined metrics.
 *
 * @author <a href="mailto:abazko@codenvy.com">Anatoliy Bazko</a>
 */
public enum MetricType {
    WORKSPACES,
    WORKSPACES_PROFILES,
    WORKSPACES_PROFILES_LIST,
    CREATED_WORKSPACES,
    CREATED_UNIQUE_WORKSPACES,
    CREATED_WORKSPACES_SET,
    DESTROYED_WORKSPACES,
    TOTAL_WORKSPACES,
    ACTIVE_WORKSPACES,
    NON_ACTIVE_WORKSPACES,
    NEW_ACTIVE_WORKSPACES,
    RETURNING_ACTIVE_WORKSPACES,
    ACTIVE_WORKSPACES_SET,
    WORKSPACES_STATISTICS_LIST,
    WORKSPACES_STATISTICS_LIST_PRECOMPUTED,
    WORKSPACES_STATISTICS,
    WORKSPACES_STATISTICS_PRECOMPUTED,

    USERS,
    CREATED_USERS,
    CREATED_UNIQUE_USERS,
    CREATED_USERS_SET,
    CREATED_USERS_FROM_AUTH,
    REMOVED_USERS,
    TOTAL_USERS,
    ACTIVE_USERS,
    ACTIVE_USERS_FROM_BEGINNING,
    ACTIVE_USERS_SET,
    NON_ACTIVE_USERS,
    NEW_ACTIVE_USERS,
    RETURNING_ACTIVE_USERS,

    USERS_LOGGED_IN_TYPES,
    USERS_LOGGED_IN_WITH_GITHUB,
    USERS_LOGGED_IN_WITH_GOOGLE,
    USERS_LOGGED_IN_WITH_FORM,
    USERS_LOGGED_IN_WITH_SYSLDAP,
    USERS_LOGGED_IN_TOTAL,
    USERS_LOGGED_IN_WITH_GITHUB_PERCENT,
    USERS_LOGGED_IN_WITH_GOOGLE_PERCENT,
    USERS_LOGGED_IN_WITH_FORM_PERCENT,
    USERS_LOGGED_IN_WITH_SYSLDAP_PERCENT,

    USER_INVITE,
    SHELL_LAUNCHED,
    USERS_ACCEPTED_INVITES,
    USERS_ACCEPTED_INVITES_PERCENT,
    USERS_ADDED_TO_WORKSPACES,
    USERS_ADDED_TO_WORKSPACES_USING_INVITATION,
    COMPLETED_PROFILES,

    PROJECTS,
    PROJECTS_LIST,
    PROJECTS_STATISTICS_LIST,
    PROJECTS_STATISTICS,
    CREATED_PROJECTS,
    DESTROYED_PROJECTS,
    TOTAL_PROJECTS,

    PROJECT_PAASES,
    PROJECT_PAAS_ANY,
    PROJECT_PAAS_AWS,
    PROJECT_PAAS_APPFOG,
    PROJECT_PAAS_CLOUDBEES,
    PROJECT_PAAS_CLOUDFOUNDRY,
    PROJECT_PAAS_GAE,
    PROJECT_PAAS_HEROKU,
    PROJECT_PAAS_OPENSHIFT,
    PROJECT_PAAS_TIER3,
    PROJECT_PAAS_MANYMO,
    PROJECT_NO_PAAS_DEFINED,

    PROJECT_TYPES,
    PROJECT_TYPE_JAR,
    PROJECT_TYPE_WAR,
    PROJECT_TYPE_JSP,
    PROJECT_TYPE_SPRING,
    PROJECT_TYPE_PHP,
    PROJECT_TYPE_DJANGO,
    PROJECT_TYPE_PYTHON,
    PROJECT_TYPE_JAVASCRIPT,
    PROJECT_TYPE_RUBY,
    PROJECT_TYPE_MMP,
    PROJECT_TYPE_NODEJS,
    PROJECT_TYPE_ANDROID,
    PROJECT_TYPE_OTHERS,

    USAGE,
    SESSIONS,
    COLLABORATIVE_SESSIONS_STARTED,
    NON_FACTORIES_PRODUCT_USAGE_SESSIONS,
    PRODUCT_USAGE_SESSIONS,
    PRODUCT_USAGE_SESSIONS_LIST,
    PRODUCT_USAGE_CONDITION_BELOW_120_MIN,
    PRODUCT_USAGE_CONDITION_BETWEEN_120_AND_300_MIN,
    PRODUCT_USAGE_CONDITION_ABOVE_300_MIN,
    TIMELINE_PRODUCT_USAGE_CONDITION_BELOW_120_MIN,
    TIMELINE_PRODUCT_USAGE_CONDITION_BETWEEN_120_AND_300_MIN,
    TIMELINE_PRODUCT_USAGE_CONDITION_ABOVE_300_MIN,
    PRODUCT_USAGE_TIME_BELOW_1_MIN,
    PRODUCT_USAGE_TIME_BETWEEN_1_AND_10_MIN,
    PRODUCT_USAGE_TIME_BETWEEN_10_AND_60_MIN,
    PRODUCT_USAGE_TIME_ABOVE_60_MIN,
    PRODUCT_USAGE_TIME_TOTAL,
    PRODUCT_USAGE_SESSIONS_BELOW_1_MIN,
    PRODUCT_USAGE_SESSIONS_BETWEEN_1_AND_10_MIN,
    PRODUCT_USAGE_SESSIONS_BETWEEN_10_AND_60_MIN,
    PRODUCT_USAGE_SESSIONS_ABOVE_60_MIN,
    PRODUCT_USAGE_USERS_BELOW_10_MIN,
    PRODUCT_USAGE_USERS_BETWEEN_10_AND_60_MIN,
    PRODUCT_USAGE_USERS_BETWEEN_60_AND_300_MIN,
    PRODUCT_USAGE_USERS_ABOVE_300_MIN,
    PRODUCT_USERS_TIME,
    PRODUCT_DOMAINS_TIME,
    PRODUCT_COMPANIES_TIME,

    RUNS_TIME,
    BUILDS_TIME,
    DEBUGS_TIME,
    TIME_IN_RUN_QUEUE,
    TIME_IN_BUILD_QUEUE,
    DOCKER_CONFIGURATION_TIME,

    RUNS,
    RUNS_WITH_TIMEOUT,
    RUNS_WITH_ALWAYS_ON,
    RUNS_FINISHED,
    RUNS_FINISHED_BY_USER,
    RUNS_FINISHED_BY_TIMEOUT,
    RUNS_MEMORY_USAGE,
    RUNS_MEMORY_USAGE_PER_HOUR,
    RUN_QUEUE_TERMINATIONS,
    BUILDS,
    BUILDS_FINISHED,
    BUILDS_FINISHED_NORMALLY,
    BUILDS_FINISHED_BY_TIMEOUT,
    BUILD_QUEUE_TERMINATIONS,
    DEPLOYS,
    DEPLOYS_TO_PAAS,
    DEBUGS,
    CODE_REFACTORINGS,
    CODE_COMPLETIONS,
    CODE_COMPLETIONS_BASED_ON_EVENT,
    CODE_COMPLETIONS_BASED_ON_IDE_USAGES,

    TOTAL_FACTORIES,
    FACTORIES,
    ACTIVE_FACTORIES_SET,
    CREATED_FACTORIES,
    CREATED_UNIQUE_FACTORIES,
    CREATED_FACTORIES_SET,
    CREATED_FACTORIES_LIST,
    FACTORY_USERS_LIST,
    FACTORY_USERS,
    FACTORY_STATISTICS_LIST,
    FACTORY_STATISTICS_LIST_PRECOMPUTED,
    FACTORY_STATISTICS,
    FACTORY_STATISTICS_PRECOMPUTED,
    PRODUCT_USAGE_FACTORY_SESSIONS_LIST,
    PRODUCT_USAGE_FACTORY_SESSIONS,
    WORKSPACES_WHERE_USERS_HAVE_SEVERAL_FACTORY_SESSIONS,
    WORKSPACES_WITH_ZERO_FACTORY_SESSIONS_LENGTH,
    CREATED_USERS_FROM_FACTORY,
    TEMPORARY_WORKSPACES_CREATED,
    REFERRERS_COUNT_TO_SPECIFIC_FACTORY,
    FACTORIES_ACCEPTED_LIST,
    FACTORIES_ACCEPTED,
    FACTORY_USED,
    FACTORIES_BUILT,
    FACTORIES_RUN,
    FACTORIES_IMPORTED,
    FACTORY_SESSIONS,
    ANONYMOUS_FACTORY_SESSIONS,
    AUTHENTICATED_FACTORY_SESSIONS,
    ABANDONED_FACTORY_SESSIONS,
    CONVERTED_FACTORY_SESSIONS,
    FACTORY_SESSIONS_WITH_BUILD,
    FACTORY_SESSIONS_WITH_RUN,
    FACTORY_SESSIONS_WITH_DEPLOY,
    FACTORY_SESSIONS_WITH_BUILD_PERCENT,
    FACTORY_SESSIONS_WITH_RUN_PERCENT,
    FACTORY_SESSIONS_WITH_DEPLOY_PERCENT,
    FACTORY_SESSIONS_BELOW_10_MIN,
    FACTORY_SESSIONS_ABOVE_10_MIN,
    FACTORY_PRODUCT_USAGE_TIME_TOTAL,

    USERS_PROFILES,
    USERS_PROFILES_LIST,
    USERS_STATISTICS,
    USERS_STATISTICS_PRECOMPUTED,
    USERS_STATISTICS_LIST,
    USERS_STATISTICS_LIST_PRECOMPUTED,
    USERS_ACTIVITY_LIST,
    USERS_ACTIVITY,

    USAGE_TIME_BY_WORKSPACES,
    USAGE_TIME_BY_WORKSPACES_LIST,
    USAGE_TIME_BY_USERS,
    USAGE_TIME_BY_USERS_LIST,

    USERS_WHO_CREATED_PROJECT,
    USERS_WHO_BUILT,
    USERS_WHO_DEPLOYED,
    USERS_WHO_DEPLOYED_TO_PAAS,
    USERS_WHO_INVITED,
    USERS_WHO_LAUNCHED_SHELL,

    TOP_FACTORY_SESSIONS,
    TOP_FACTORIES,
    TOP_REFERRERS,
    TOP_USERS,
    TOP_DOMAINS,
    TOP_COMPANIES,

    IDE_USAGES,
    ZERO,

    ACCOUNTS_LIST,
    ACCOUNT_USERS_ROLES_LIST,
    ACCOUNT_USERS_ROLES,
    ACCOUNT_WORKSPACES_LIST,
    ACCOUNT_SUBSCRIPTIONS_LIST,

    TIME_TRACKING_USERS,
    TIME_TRACKING_WORKSPACES,
    TIME_TRACKING_SESSIONS,
}
