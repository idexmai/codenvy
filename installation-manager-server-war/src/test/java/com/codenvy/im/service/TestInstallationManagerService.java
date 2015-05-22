/*
 * CODENVY CONFIDENTIAL
 * __________________
 *
 *  [2012] - [2015] Codenvy, S.A.
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
package com.codenvy.im.service;

import com.codenvy.im.BaseTest;
import com.codenvy.im.artifacts.ArtifactFactory;
import com.codenvy.im.artifacts.CDECArtifact;
import com.codenvy.im.facade.InstallationManagerFacade;
import com.codenvy.im.managers.BackupConfig;
import com.codenvy.im.managers.Config;
import com.codenvy.im.managers.ConfigManager;
import com.codenvy.im.managers.InstallOptions;
import com.codenvy.im.managers.InstallType;
import com.codenvy.im.request.Request;
import com.codenvy.im.response.ResponseCode;
import com.codenvy.im.saas.SaasAccountServiceProxy;
import com.codenvy.im.saas.SaasUserCredentials;
import com.codenvy.im.utils.Commons;
import com.codenvy.im.utils.HttpException;
import com.codenvy.im.utils.Version;
import com.google.common.collect.ImmutableMap;

import org.eclipse.che.api.account.shared.dto.SubscriptionDescriptor;
import org.eclipse.che.api.auth.AuthenticationException;
import org.eclipse.che.api.auth.server.dto.DtoServerImpls;
import org.eclipse.che.api.auth.shared.dto.Credentials;
import org.eclipse.che.dto.server.DtoFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Dmytro Nochevnov
 */
public class TestInstallationManagerService extends BaseTest {

    public static final String CODENVY_ARTIFACT_NAME = "codenvy";
    public static final String TEST_VERSION          = "1.0.0";
    public static final String TEST_ACCESS_TOKEN     = "accessToken";
    public static final String TEST_ACCOUNT_ID       = "accountId";
    public static final String TEST_ACCOUNT_NAME     = "account";
    public static final String TEST_USER_NAME        = "user";
    public static final String TEST_USER_PASSWORD    = "password";

    public static final String TEST_SYSTEM_ADMIN_NAME = "admin";

    private static final String TEST_CREDENTIALS_JSON            = "{\n"
                                                                   + "  \"username\": \"" + TEST_USER_NAME + "\",\n"
                                                                   + "  \"password\": \"" + TEST_USER_PASSWORD + "\"\n"
                                                                   + "}";

    private InstallationManagerService service;

    @Mock
    private InstallationManagerFacade mockFacade;
    @Mock
    private ConfigManager             configManager;
    @Mock
    private Principal                 mockPrincipal;
    @Mock
    private SecurityContext           mockSecurityContext;
    @Mock
    private Config                    mockConfig;

    private com.codenvy.im.response.Response mockFacadeOkResponse = new com.codenvy.im.response.Response().setStatus(ResponseCode.OK);

    private com.codenvy.im.response.Response mockFacadeErrorResponse = new com.codenvy.im.response.Response().setStatus(ResponseCode.ERROR)
                                                                                                             .setMessage("error");

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = spy(new InstallationManagerService(mockFacade, configManager));

        doReturn(mockPrincipal).when(mockSecurityContext).getUserPrincipal();
        doReturn(TEST_SYSTEM_ADMIN_NAME).when(mockPrincipal).getName();
    }

    @Test
    public void testStartDownload() throws Exception {
        Request testRequest = new Request().setArtifactName(CODENVY_ARTIFACT_NAME)
                                           .setVersion(TEST_VERSION);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).startDownload(testRequest);
        Response result = service.startDownload(CODENVY_ARTIFACT_NAME, TEST_VERSION);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).startDownload(testRequest);
        result = service.startDownload(CODENVY_ARTIFACT_NAME, TEST_VERSION);
        assertErrorResponse(result);
    }

    @Test
    public void testStopDownload() throws Exception {
        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).stopDownload();
        Response result = service.stopDownload();
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).stopDownload();
        result = service.stopDownload();
        assertErrorResponse(result);
    }

    @Test
    public void testGetDownloadStatus() throws Exception {
        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).getDownloadStatus();
        Response result = service.getDownloadStatus();
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).getDownloadStatus();
        result = service.getDownloadStatus();
        assertErrorResponse(result);
    }

    @Test
    public void testGetUpdates() throws Exception {
        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).getUpdates();
        Response result = service.getUpdates();
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).getUpdates();
        result = service.getUpdates();
        assertErrorResponse(result);
    }

    @Test
    public void testGetDownloads() throws Exception {
        Request testRequest = new Request().setArtifactName(CODENVY_ARTIFACT_NAME);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).getDownloads(testRequest);
        Response result = service.getDownloads(CODENVY_ARTIFACT_NAME);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).getDownloads(testRequest);
        result = service.getDownloads(CODENVY_ARTIFACT_NAME);
        assertErrorResponse(result);
    }

    @Test
    public void testGetInstalledVersions() throws Exception {
        doReturn(mockFacadeOkResponse).when(mockFacade).getInstalledVersions();
        Response result = service.getInstalledVersions();
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse).when(mockFacade).getInstalledVersions();
        result = service.getInstalledVersions();
        assertErrorResponse(result);
    }

    @Test
    public void testUpdateCodenvy() throws Exception {
        doReturn(InstallType.SINGLE_SERVER).when(configManager).detectInstallationType();
        doReturn("3.1.0").when(mockFacade).getVersionToInstall(any(Request.class));

        Map<String, String> testConfigProperties = new HashMap<>();
        testConfigProperties.put("property1", "value1");
        testConfigProperties.put("property2", "value2");

        doReturn(testConfigProperties).when(configManager).prepareInstallProperties(null,
                                                                                    InstallType.SINGLE_SERVER,
                                                                                    ArtifactFactory.createArtifact(CODENVY_ARTIFACT_NAME),
                                                                                    Version.valueOf("3.1.0"));

        int testStep = 1;
        InstallOptions testInstallOptions = new InstallOptions().setInstallType(InstallType.SINGLE_SERVER)
                                                                .setConfigProperties(testConfigProperties)
                                                                .setStep(testStep);

        Request testRequest = new Request().setArtifactName(CODENVY_ARTIFACT_NAME)
                                           .setVersion("3.1.0")
                                           .setInstallOptions(testInstallOptions);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).install(testRequest);
        Response result = service.updateCodenvy(testStep);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).install(testRequest);
        result = service.updateCodenvy(testStep);
        assertErrorResponse(result);
    }

    @Test
    public void testUpdateCodenvyWhenConfigNull() throws Exception {
        doReturn(InstallType.SINGLE_SERVER).when(configManager).detectInstallationType();

        int testStep = 1;
        InstallOptions testInstallOptions = new InstallOptions().setInstallType(InstallType.SINGLE_SERVER)
                                                                .setConfigProperties(new HashMap<String, String>())
                                                                .setStep(testStep);

        Request testRequest = new Request().setArtifactName(CODENVY_ARTIFACT_NAME)
                                           .setInstallOptions(testInstallOptions);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).install(testRequest);
        Response result = service.updateCodenvy(testStep);
        assertOkResponse(result);
    }

    @Test
    public void testGetUpdateCodenvyInfo() throws Exception {
        doReturn(InstallType.SINGLE_SERVER).when(configManager).detectInstallationType();

        InstallOptions testInstallOptions = new InstallOptions().setInstallType(InstallType.SINGLE_SERVER);

        Request testRequest = new Request().setArtifactName(CDECArtifact.NAME)
                                           .setInstallOptions(testInstallOptions);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).getInstallInfo(testRequest);
        Response result = service.getUpdateCodenvyInfo();
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).getInstallInfo(testRequest);
        result = service.getUpdateCodenvyInfo();
        assertErrorResponse(result);
    }

    @Test
    public void testGetConfig() throws Exception {
        doReturn(Collections.emptyMap()).when(mockFacade).getInstallationManagerProperties();
        Response response = service.getInstallationManagerServerConfig();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testAddNode() throws Exception {
        doReturn(mockFacadeOkResponse).when(mockFacade).addNode("dns");
        Response result = service.addNode("dns");
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse).when(mockFacade).addNode("dns");
        result = service.addNode("dns");
        assertErrorResponse(result);
    }

    @Test
    public void testRemoveNode() throws Exception {
        doReturn(mockFacadeOkResponse).when(mockFacade).removeNode("dns");
        Response result = service.removeNode("dns");
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse).when(mockFacade).removeNode("dns");
        result = service.removeNode("dns");
        assertErrorResponse(result);
    }

    @Test
    public void testBackup() throws Exception {
        String testBackupDirectoryPath = "test/path";
        BackupConfig testBackupConfig = new BackupConfig().setArtifactName(CODENVY_ARTIFACT_NAME)
                                                          .setBackupDirectory(testBackupDirectoryPath);

        doReturn(mockFacadeOkResponse).when(mockFacade).backup(testBackupConfig);
        Response result = service.backup(CODENVY_ARTIFACT_NAME, testBackupDirectoryPath);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse).when(mockFacade).backup(testBackupConfig);
        result = service.backup(CODENVY_ARTIFACT_NAME, testBackupDirectoryPath);
        assertErrorResponse(result);
    }

    @Test
    public void testRestore() throws Exception {
        String testBackupFilePath = "test/path/backup";
        BackupConfig testBackupConfig = new BackupConfig().setArtifactName(CODENVY_ARTIFACT_NAME)
                                                          .setBackupFile(testBackupFilePath);

        doReturn(mockFacadeOkResponse).when(mockFacade).restore(testBackupConfig);
        Response result = service.restore(CODENVY_ARTIFACT_NAME, testBackupFilePath);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse).when(mockFacade).restore(testBackupConfig);
        result = service.restore(CODENVY_ARTIFACT_NAME, testBackupFilePath);
        assertErrorResponse(result);
    }

    @Test
    public void testAddTrialSubscription() throws Exception {
        SaasUserCredentials testUserCredentials = new SaasUserCredentials(TEST_ACCESS_TOKEN, TEST_ACCOUNT_ID);
        Request testRequest = new Request().setSaasUserCredentials(testUserCredentials);

        doReturn(mockFacadeOkResponse.toJson()).when(mockFacade).addTrialSaasSubscription(testRequest);

        service.users.put(TEST_SYSTEM_ADMIN_NAME, testUserCredentials);

        Response result = service.addTrialSubscription(mockSecurityContext);
        assertOkResponse(result);

        doReturn(mockFacadeErrorResponse.toJson()).when(mockFacade).addTrialSaasSubscription(testRequest);
        result = service.addTrialSubscription(mockSecurityContext);
        assertErrorResponse(result);
    }

    @Test
    public void testGetSubscription() throws Exception {
        SaasUserCredentials testUserCredentials = new SaasUserCredentials(TEST_ACCOUNT_ID, TEST_ACCESS_TOKEN);
        Request testRequest = new Request().setSaasUserCredentials(testUserCredentials);

        SimpleDateFormat subscriptionDateFormat = new SimpleDateFormat(SaasAccountServiceProxy.SUBSCRIPTION_DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String startDate = subscriptionDateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, 2);
        String endDate = subscriptionDateFormat.format(cal.getTime());

        String testSubscriptionDescriptorJson = "{\n"
                                                + "  \"description\": \"On-Prem Commercial 25 Users\",\n"
                                                + "  \"startDate\" : \"" + startDate + "\",\n"
                                                + "  \"endDate\" : \"" + endDate + "\",\n"
                                                + "  \"links\": [\n"
                                                + "    {\n"
                                                + "      \"href\": \"https://codenvy-stg"
                                                + ".com/api/account/subscriptions/subscriptionoxmrh93dw3ceuegk\",\n"
                                                + "      \"rel\": \"get subscription by id\",\n"
                                                + "      \"produces\": \"application/json\",\n"
                                                + "      \"parameters\": [],\n"
                                                + "      \"method\": \"GET\"\n"
                                                + "    }\n"
                                                + "  ],\n"
                                                + "  \"properties\": {\n"
                                                + "    \"Users\": \"25\",\n"
                                                + "    \"Package\": \"Commercial\"\n"
                                                + "  },\n"
                                                + "  \"id\": \"subscriptionoxmrh93dw3ceuegk\",\n"
                                                + "  \"state\": \"ACTIVE\"\n"
                                                + "}";
        SubscriptionDescriptor descriptor = DtoFactory.getInstance().createDtoFromJson(testSubscriptionDescriptorJson, SubscriptionDescriptor.class);
        doReturn(descriptor).when(mockFacade).getSubscriptionDescriptor(SaasAccountServiceProxy.ON_PREMISES, testRequest);

        service.users.put(TEST_SYSTEM_ADMIN_NAME, testUserCredentials);

        Response result = service.getSaasSubscription(mockSecurityContext);
        assertEquals(result.getStatus(), Response.Status.OK.getStatusCode());
        SubscriptionDescriptor subscription = Commons.createDtoFromJson((String)result.getEntity(), SubscriptionDescriptor.class);
        assertEquals(subscription.getDescription(), "On-Prem Commercial 25 Users");
        assertEquals(subscription.getStartDate(), startDate);
        assertEquals(subscription.getEndDate(), endDate);
        assertTrue(subscription.getLinks().isEmpty());
        assertEquals(subscription.getId(), "subscriptionoxmrh93dw3ceuegk");
        assertEquals(subscription.getState().name(), "ACTIVE");
        assertEquals(subscription.getProperties().get("Users"), "25");
        assertEquals(subscription.getProperties().get("Package"), "Commercial");

        doThrow(new HttpException(500, "error")).when(mockFacade).getSubscriptionDescriptor(SaasAccountServiceProxy.ON_PREMISES, testRequest);
        result = service.getSaasSubscription(mockSecurityContext);
        assertErrorResponse(result);
    }

    @Test
    public void testLoginToSaas() throws Exception {
        Credentials testSaasUsernameAndPassword = Commons.createDtoFromJson(TEST_CREDENTIALS_JSON, Credentials.class);
        Request testRequest = new Request().setSaasUserCredentials(new SaasUserCredentials(TEST_ACCESS_TOKEN));

        doReturn(new DtoServerImpls.TokenImpl().withValue(TEST_ACCESS_TOKEN)).when(mockFacade).loginToCodenvySaaS(testSaasUsernameAndPassword);
        doReturn(new org.eclipse.che.api.account.server.dto.DtoServerImpls.AccountReferenceImpl().withId(TEST_ACCOUNT_ID).withName(TEST_ACCOUNT_NAME))
                .when(mockFacade).getAccountWhereUserIsOwner(null, testRequest);

        Response result = service.loginToCodenvySaas(testSaasUsernameAndPassword, mockSecurityContext);
        assertEquals(result.getStatus(), Response.Status.OK.getStatusCode());

        assertEquals(service.users.size(), 1);
        assertTrue(service.users.containsKey(TEST_SYSTEM_ADMIN_NAME));

        SaasUserCredentials testSaasSaasUserCredentials = service.users.get(TEST_SYSTEM_ADMIN_NAME);
        assertEquals(testSaasSaasUserCredentials.getAccountId(), TEST_ACCOUNT_ID);
        assertEquals(testSaasSaasUserCredentials.getToken(), TEST_ACCESS_TOKEN);
    }

    @Test
    public void testLoginToSaasWhenHttpException() throws Exception {
        Credentials testSaasUsernameAndPassword = Commons.createDtoFromJson(TEST_CREDENTIALS_JSON, Credentials.class);
        Request testRequest = new Request().setSaasUserCredentials(new SaasUserCredentials(TEST_ACCESS_TOKEN));

        doThrow(AuthenticationException.class).when(mockFacade).loginToCodenvySaaS(testSaasUsernameAndPassword);
        Response result = service.loginToCodenvySaas(testSaasUsernameAndPassword, mockSecurityContext);
        assertEquals(result.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());

        doReturn(new DtoServerImpls.TokenImpl().withValue(TEST_ACCESS_TOKEN)).when(mockFacade).loginToCodenvySaaS(testSaasUsernameAndPassword);
        doThrow(new HttpException(500, "Login error")).when(mockFacade).getAccountWhereUserIsOwner(null, testRequest);
        result = service.loginToCodenvySaas(testSaasUsernameAndPassword, mockSecurityContext);
        assertEquals(result.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }


    @Test
    public void testLoginToSaasWhenNullAccountReference() throws Exception {
        Credentials testSaasUsernameAndPassword = Commons.createDtoFromJson(TEST_CREDENTIALS_JSON, Credentials.class);
        Request testRequest = new Request().setSaasUserCredentials(new SaasUserCredentials(TEST_ACCESS_TOKEN));

        doReturn(new DtoServerImpls.TokenImpl().withValue(TEST_ACCESS_TOKEN)).when(mockFacade).loginToCodenvySaaS(testSaasUsernameAndPassword);
        doReturn(null).when(mockFacade).getAccountWhereUserIsOwner(null, testRequest);

        Response response = service.loginToCodenvySaas(testSaasUsernameAndPassword, mockSecurityContext);
        assertEquals(response.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void testGetNodeConfig() throws IOException {
        Config testConfig = new Config(new LinkedHashMap<>(ImmutableMap.of(
                "builder_host_name", "builder1.dev.com",
                "additional_runners", "http://runner1.dev.com:8080/runner/internal/runner,http://runner2.dev.com:8080/runner/internal/runner",
                "analytics_host_name", "analytics.dev.com",
                "additional_builders", "",
                "data_host_name", "data.dev.com"
                                                                          )));
        doReturn(testConfig).when(configManager).loadInstalledCodenvyConfig(InstallType.MULTI_SERVER);
        doReturn(InstallType.MULTI_SERVER).when(configManager).detectInstallationType();

        Response result = service.getNodesList();
        assertEquals(result.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(result.getEntity(), "{\n"
                                         + "  \"builder_host_name\" : \"builder1.dev.com\",\n"
                                         + "  \"additional_runners\" : [ \"runner1.dev.com\", \"runner2.dev.com\" ],\n"
                                         + "  \"analytics_host_name\" : \"analytics.dev.com\",\n"
                                         + "  \"additional_builders\" : [ ],\n"
                                         + "  \"data_host_name\" : \"data.dev.com\"\n"
                                         + "}");
    }

    @Test
    public void testGetNodeConfigWhenSingleNode() throws IOException {
        Config config = mock(Config.class);
        doReturn("local").when(config).getHostUrl();

        doReturn(InstallType.SINGLE_SERVER).when(configManager).detectInstallationType();
        doReturn(config).when(configManager).loadInstalledCodenvyConfig(InstallType.SINGLE_SERVER);

        Response result = service.getNodesList();
        assertEquals(result.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(result.getEntity(), "{\n" +
                                         "  \"host_url\" : \"local\"\n" +
                                         "}");
    }

    @Test
    public void testGetNodeConfigError() throws IOException {
        doThrow(new RuntimeException("error")).when(configManager).detectInstallationType();

        Response result = service.getNodesList();
        assertEquals(result.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertEquals(result.getEntity(), "{\n"
                                         + "  \"message\" : \"error\",\n"
                                         + "  \"status\" : \"ERROR\"\n"
                                         + "}");
    }

    @Test
    public void testGetArtifactPropertiesErrorIfArtifactNotFound() throws Exception {
        Response response = service.getArtifactProperties("artifact", "1.3.1");
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testGetArtifactPropertiesErrorIfVersionInvalid() throws Exception {
        Response response = service.getArtifactProperties("codenvy", "version");
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testGetArtifactProperties() throws Exception {
        Response response = service.getArtifactProperties("codenvy", "1.0.1");
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testLoadPropertyShouldReturnOkResponse() throws Exception {
        doReturn(Collections.emptyMap()).when(mockFacade).loadProperties(anyCollection());

        Response response = service.loadProperty(Collections.<String>emptyList());

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testLoadPropertyShouldReturnErrorResponse() throws Exception {
        doThrow(IOException.class).when(mockFacade).loadProperties(anyCollection());

        Response response = service.loadProperty(Collections.<String>emptyList());

        assertEquals(response.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    @Test
    public void testStorePropertyShouldReturnOkResponse() throws Exception {
        doNothing().when(mockFacade).storeProperties(anyMap());

        Response response = service.storeProperty(Collections.<String, String>emptyMap());

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void testStorePropertyShouldReturnErrorResponse() throws Exception {
        doThrow(IOException.class).when(mockFacade).storeProperties(anyMap());

        Response response = service.storeProperty(Collections.<String, String>emptyMap());

        assertEquals(response.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    private void assertOkResponse(Response result) throws IOException {
        assertEquals(result.getStatus(), Response.Status.OK.getStatusCode());
        String facadeResponse = (String)result.getEntity();
        assertEquals(facadeResponse, mockFacadeOkResponse.toJson());
    }

    private void assertErrorResponse(Response result) {
        assertEquals(result.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        String facadeResponse = (String)result.getEntity();
        assertEquals(facadeResponse, mockFacadeErrorResponse.toJson());
    }
}

