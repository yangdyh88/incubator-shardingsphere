/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.merger.dal.show;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.core.constant.ShardingConstant;
import org.apache.shardingsphere.core.merger.MergedResult;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.util.Collections;
import java.util.List;

/**
 * Merged result for show databases.
 *
 * @author chenqingyang
 */
@RequiredArgsConstructor
public final class ShowDatabasesMergedResult extends LocalMergedResultAdapter implements MergedResult {
    
    private final List<String> schemas;
    
    private int currentIndex;
    
    public ShowDatabasesMergedResult() {
        this(Collections.singletonList(ShardingConstant.LOGIC_SCHEMA_NAME));
    }
    
    @Override
    public boolean next() {
        return currentIndex++ < schemas.size();
    }
    
    @Override
    public Object getValue(final int columnIndex, final Class<?> type) throws SQLException {
        if (Blob.class == type || Clob.class == type || Reader.class == type || InputStream.class == type || SQLXML.class == type) {
            throw new SQLFeatureNotSupportedException();
        }
        return schemas.get(currentIndex - 1);
    }
}
