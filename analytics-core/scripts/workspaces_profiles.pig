/*
 *
 * CODENVY CONFIDENTIAL
 * ________________
 *
 * [2012] - [2014] Codenvy, S.A.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */

IMPORT 'macros.pig';

l = loadResources('$LOG', '$FROM_DATE', '$TO_DATE', '$USER', '$WS');

----------------------------------------------------------------------------------
------------------------------ ws creation processing ----------------------------
----------------------------------------------------------------------------------
a1 = filterByEvent(l, 'workspace-created');
a2 = extractParam(a1, 'WS-ID', 'wsId');
a = FOREACH a2 GENERATE dt,
                        (wsId IS NOT NULL ? wsId : ReplaceWsWithId(wsName)) AS wsId,
                        wsName;

resultA = FOREACH a GENERATE LOWER(wsId),
                             TOTUPLE('date', ToMilliSeconds(dt)),
                             TOTUPLE('ws_name', LOWER(wsName)),
                             TOTUPLE('persistent_ws', (IsTemporaryWorkspaceByName(wsName) ? 0 : 1));
STORE resultA INTO '$STORAGE_URL.$STORAGE_TABLE' USING MongoStorage;

----------------------------------------------------------------------------------
------------------------------ ws updating processing ----------------------------
----------------------------------------------------------------------------------
b1 = filterByEvent(l, 'workspace-updated');
b2 = extractParam(b1, 'WS-ID', 'wsId');
b = FOREACH b2 GENERATE dt, wsId, wsName;

c1 = lastUpdate(b, 'wsId');
c = FOREACH c1 GENERATE b::wsId AS wsId,
                        b::wsName AS wsName;

resultC = FOREACH c GENERATE LOWER(wsId),
                             TOTUPLE('ws_name', LOWER(wsName)),
                             TOTUPLE('persistent_ws', (IsTemporaryWorkspaceByName(wsName) ? 0 : 1));
STORE resultC INTO '$STORAGE_URL.$STORAGE_TABLE' USING MongoStorage;