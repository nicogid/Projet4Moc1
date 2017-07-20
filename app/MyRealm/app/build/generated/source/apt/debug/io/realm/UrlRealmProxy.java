package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UrlRealmProxy extends com.example.gidon.myrealm.Model.Url
    implements RealmObjectProxy, UrlRealmProxyInterface {

    static final class UrlColumnInfo extends ColumnInfo {
        long TitleIndex;
        long DescriptionIndex;
        long tagIndex;

        UrlColumnInfo(SharedRealm realm, Table table) {
            super(3);
            this.TitleIndex = addColumnDetails(table, "Title", RealmFieldType.STRING);
            this.DescriptionIndex = addColumnDetails(table, "Description", RealmFieldType.STRING);
            this.tagIndex = addColumnDetails(table, "tag", RealmFieldType.LIST);
        }

        UrlColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UrlColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UrlColumnInfo src = (UrlColumnInfo) rawSrc;
            final UrlColumnInfo dst = (UrlColumnInfo) rawDst;
            dst.TitleIndex = src.TitleIndex;
            dst.DescriptionIndex = src.DescriptionIndex;
            dst.tagIndex = src.tagIndex;
        }
    }

    private UrlColumnInfo columnInfo;
    private ProxyState<com.example.gidon.myrealm.Model.Url> proxyState;
    private RealmList<com.example.gidon.myrealm.Model.Tag> tagRealmList;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("Title");
        fieldNames.add("Description");
        fieldNames.add("tag");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    UrlRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UrlColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.gidon.myrealm.Model.Url>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$Title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.TitleIndex);
    }

    @Override
    public void realmSet$Title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.TitleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.TitleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.TitleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.TitleIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$Description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.DescriptionIndex);
    }

    @Override
    public void realmSet$Description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.DescriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.DescriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.DescriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.DescriptionIndex, value);
    }

    @Override
    public RealmList<com.example.gidon.myrealm.Model.Tag> realmGet$tag() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (tagRealmList != null) {
            return tagRealmList;
        } else {
            LinkView linkView = proxyState.getRow$realm().getLinkList(columnInfo.tagIndex);
            tagRealmList = new RealmList<com.example.gidon.myrealm.Model.Tag>(com.example.gidon.myrealm.Model.Tag.class, linkView, proxyState.getRealm$realm());
            return tagRealmList;
        }
    }

    @Override
    public void realmSet$tag(RealmList<com.example.gidon.myrealm.Model.Tag> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("tag")) {
                return;
            }
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.gidon.myrealm.Model.Tag> original = value;
                value = new RealmList<com.example.gidon.myrealm.Model.Tag>();
                for (com.example.gidon.myrealm.Model.Tag item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        LinkView links = proxyState.getRow$realm().getLinkList(columnInfo.tagIndex);
        links.clear();
        if (value == null) {
            return;
        }
        for (RealmModel linkedObject : (RealmList<? extends RealmModel>) value) {
            if (!(RealmObject.isManaged(linkedObject) && RealmObject.isValid(linkedObject))) {
                throw new IllegalArgumentException("Each element of 'value' must be a valid managed object.");
            }
            if (((RealmObjectProxy)linkedObject).realmGet$proxyState().getRealm$realm() != proxyState.getRealm$realm()) {
                throw new IllegalArgumentException("Each element of 'value' must belong to the same Realm.");
            }
            links.add(((RealmObjectProxy)linkedObject).realmGet$proxyState().getRow$realm().getIndex());
        }
    }

    public static RealmObjectSchema createRealmObjectSchema(RealmSchema realmSchema) {
        if (!realmSchema.contains("Url")) {
            RealmObjectSchema realmObjectSchema = realmSchema.create("Url");
            realmObjectSchema.add("Title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            realmObjectSchema.add("Description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
            if (!realmSchema.contains("Tag")) {
                TagRealmProxy.createRealmObjectSchema(realmSchema);
            }
            realmObjectSchema.add("tag", RealmFieldType.LIST, realmSchema.get("Tag"));
            return realmObjectSchema;
        }
        return realmSchema.get("Url");
    }

    public static UrlColumnInfo validateTable(SharedRealm sharedRealm, boolean allowExtraColumns) {
        if (!sharedRealm.hasTable("class_Url")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "The 'Url' class is missing from the schema for this Realm.");
        }
        Table table = sharedRealm.getTable("class_Url");
        final long columnCount = table.getColumnCount();
        if (columnCount != 3) {
            if (columnCount < 3) {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is less than expected - expected 3 but was " + columnCount);
            }
            if (allowExtraColumns) {
                RealmLog.debug("Field count is more than expected - expected 3 but was %1$d", columnCount);
            } else {
                throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field count is more than expected - expected 3 but was " + columnCount);
            }
        }
        Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
        for (long i = 0; i < columnCount; i++) {
            columnTypes.put(table.getColumnName(i), table.getColumnType(i));
        }

        final UrlColumnInfo columnInfo = new UrlColumnInfo(sharedRealm, table);

        if (table.hasPrimaryKey()) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Primary Key defined for field " + table.getColumnName(table.getPrimaryKey()) + " was removed.");
        }

        if (!columnTypes.containsKey("Title")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'Title' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("Title") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'Title' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.TitleIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'Title' is required. Either set @Required to field 'Title' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("Description")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'Description' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
        }
        if (columnTypes.get("Description") != RealmFieldType.STRING) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'String' for field 'Description' in existing Realm file.");
        }
        if (!table.isColumnNullable(columnInfo.DescriptionIndex)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Field 'Description' is required. Either set @Required to field 'Description' or migrate using RealmObjectSchema.setNullable().");
        }
        if (!columnTypes.containsKey("tag")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing field 'tag'");
        }
        if (columnTypes.get("tag") != RealmFieldType.LIST) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid type 'Tag' for field 'tag'");
        }
        if (!sharedRealm.hasTable("class_Tag")) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Missing class 'class_Tag' for field 'tag'");
        }
        Table table_2 = sharedRealm.getTable("class_Tag");
        if (!table.getLinkTarget(columnInfo.tagIndex).hasSameSchema(table_2)) {
            throw new RealmMigrationNeededException(sharedRealm.getPath(), "Invalid RealmList type for field 'tag': '" + table.getLinkTarget(columnInfo.tagIndex).getName() + "' expected - was '" + table_2.getName() + "'");
        }

        return columnInfo;
    }

    public static String getTableName() {
        return "class_Url";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.example.gidon.myrealm.Model.Url createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("tag")) {
            excludeFields.add("tag");
        }
        com.example.gidon.myrealm.Model.Url obj = realm.createObjectInternal(com.example.gidon.myrealm.Model.Url.class, true, excludeFields);
        if (json.has("Title")) {
            if (json.isNull("Title")) {
                ((UrlRealmProxyInterface) obj).realmSet$Title(null);
            } else {
                ((UrlRealmProxyInterface) obj).realmSet$Title((String) json.getString("Title"));
            }
        }
        if (json.has("Description")) {
            if (json.isNull("Description")) {
                ((UrlRealmProxyInterface) obj).realmSet$Description(null);
            } else {
                ((UrlRealmProxyInterface) obj).realmSet$Description((String) json.getString("Description"));
            }
        }
        if (json.has("tag")) {
            if (json.isNull("tag")) {
                ((UrlRealmProxyInterface) obj).realmSet$tag(null);
            } else {
                ((UrlRealmProxyInterface) obj).realmGet$tag().clear();
                JSONArray array = json.getJSONArray("tag");
                for (int i = 0; i < array.length(); i++) {
                    com.example.gidon.myrealm.Model.Tag item = TagRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    ((UrlRealmProxyInterface) obj).realmGet$tag().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.gidon.myrealm.Model.Url createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        com.example.gidon.myrealm.Model.Url obj = new com.example.gidon.myrealm.Model.Url();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("Title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UrlRealmProxyInterface) obj).realmSet$Title(null);
                } else {
                    ((UrlRealmProxyInterface) obj).realmSet$Title((String) reader.nextString());
                }
            } else if (name.equals("Description")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UrlRealmProxyInterface) obj).realmSet$Description(null);
                } else {
                    ((UrlRealmProxyInterface) obj).realmSet$Description((String) reader.nextString());
                }
            } else if (name.equals("tag")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((UrlRealmProxyInterface) obj).realmSet$tag(null);
                } else {
                    ((UrlRealmProxyInterface) obj).realmSet$tag(new RealmList<com.example.gidon.myrealm.Model.Tag>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.gidon.myrealm.Model.Tag item = TagRealmProxy.createUsingJsonStream(realm, reader);
                        ((UrlRealmProxyInterface) obj).realmGet$tag().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        obj = realm.copyToRealm(obj);
        return obj;
    }

    public static com.example.gidon.myrealm.Model.Url copyOrUpdate(Realm realm, com.example.gidon.myrealm.Model.Url object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.gidon.myrealm.Model.Url) cachedRealmObject;
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static com.example.gidon.myrealm.Model.Url copy(Realm realm, com.example.gidon.myrealm.Model.Url newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.gidon.myrealm.Model.Url) cachedRealmObject;
        } else {
            // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
            com.example.gidon.myrealm.Model.Url realmObject = realm.createObjectInternal(com.example.gidon.myrealm.Model.Url.class, false, Collections.<String>emptyList());
            cache.put(newObject, (RealmObjectProxy) realmObject);
            ((UrlRealmProxyInterface) realmObject).realmSet$Title(((UrlRealmProxyInterface) newObject).realmGet$Title());
            ((UrlRealmProxyInterface) realmObject).realmSet$Description(((UrlRealmProxyInterface) newObject).realmGet$Description());

            RealmList<com.example.gidon.myrealm.Model.Tag> tagList = ((UrlRealmProxyInterface) newObject).realmGet$tag();
            if (tagList != null) {
                RealmList<com.example.gidon.myrealm.Model.Tag> tagRealmList = ((UrlRealmProxyInterface) realmObject).realmGet$tag();
                for (int i = 0; i < tagList.size(); i++) {
                    com.example.gidon.myrealm.Model.Tag tagItem = tagList.get(i);
                    com.example.gidon.myrealm.Model.Tag cachetag = (com.example.gidon.myrealm.Model.Tag) cache.get(tagItem);
                    if (cachetag != null) {
                        tagRealmList.add(cachetag);
                    } else {
                        tagRealmList.add(TagRealmProxy.copyOrUpdate(realm, tagList.get(i), update, cache));
                    }
                }
            }

            return realmObject;
        }
    }

    public static long insert(Realm realm, com.example.gidon.myrealm.Model.Url object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.gidon.myrealm.Model.Url.class);
        long tableNativePtr = table.getNativePtr();
        UrlColumnInfo columnInfo = (UrlColumnInfo) realm.schema.getColumnInfo(com.example.gidon.myrealm.Model.Url.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$Title = ((UrlRealmProxyInterface)object).realmGet$Title();
        if (realmGet$Title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.TitleIndex, rowIndex, realmGet$Title, false);
        }
        String realmGet$Description = ((UrlRealmProxyInterface)object).realmGet$Description();
        if (realmGet$Description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, realmGet$Description, false);
        }

        RealmList<com.example.gidon.myrealm.Model.Tag> tagList = ((UrlRealmProxyInterface) object).realmGet$tag();
        if (tagList != null) {
            long tagNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.tagIndex, rowIndex);
            for (com.example.gidon.myrealm.Model.Tag tagItem : tagList) {
                Long cacheItemIndextag = cache.get(tagItem);
                if (cacheItemIndextag == null) {
                    cacheItemIndextag = TagRealmProxy.insert(realm, tagItem, cache);
                }
                LinkView.nativeAdd(tagNativeLinkViewPtr, cacheItemIndextag);
            }
        }

        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gidon.myrealm.Model.Url.class);
        long tableNativePtr = table.getNativePtr();
        UrlColumnInfo columnInfo = (UrlColumnInfo) realm.schema.getColumnInfo(com.example.gidon.myrealm.Model.Url.class);
        com.example.gidon.myrealm.Model.Url object = null;
        while (objects.hasNext()) {
            object = (com.example.gidon.myrealm.Model.Url) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$Title = ((UrlRealmProxyInterface)object).realmGet$Title();
                if (realmGet$Title != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.TitleIndex, rowIndex, realmGet$Title, false);
                }
                String realmGet$Description = ((UrlRealmProxyInterface)object).realmGet$Description();
                if (realmGet$Description != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, realmGet$Description, false);
                }

                RealmList<com.example.gidon.myrealm.Model.Tag> tagList = ((UrlRealmProxyInterface) object).realmGet$tag();
                if (tagList != null) {
                    long tagNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.tagIndex, rowIndex);
                    for (com.example.gidon.myrealm.Model.Tag tagItem : tagList) {
                        Long cacheItemIndextag = cache.get(tagItem);
                        if (cacheItemIndextag == null) {
                            cacheItemIndextag = TagRealmProxy.insert(realm, tagItem, cache);
                        }
                        LinkView.nativeAdd(tagNativeLinkViewPtr, cacheItemIndextag);
                    }
                }

            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.gidon.myrealm.Model.Url object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.gidon.myrealm.Model.Url.class);
        long tableNativePtr = table.getNativePtr();
        UrlColumnInfo columnInfo = (UrlColumnInfo) realm.schema.getColumnInfo(com.example.gidon.myrealm.Model.Url.class);
        long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
        cache.put(object, rowIndex);
        String realmGet$Title = ((UrlRealmProxyInterface)object).realmGet$Title();
        if (realmGet$Title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.TitleIndex, rowIndex, realmGet$Title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.TitleIndex, rowIndex, false);
        }
        String realmGet$Description = ((UrlRealmProxyInterface)object).realmGet$Description();
        if (realmGet$Description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, realmGet$Description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, false);
        }

        long tagNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.tagIndex, rowIndex);
        LinkView.nativeClear(tagNativeLinkViewPtr);
        RealmList<com.example.gidon.myrealm.Model.Tag> tagList = ((UrlRealmProxyInterface) object).realmGet$tag();
        if (tagList != null) {
            for (com.example.gidon.myrealm.Model.Tag tagItem : tagList) {
                Long cacheItemIndextag = cache.get(tagItem);
                if (cacheItemIndextag == null) {
                    cacheItemIndextag = TagRealmProxy.insertOrUpdate(realm, tagItem, cache);
                }
                LinkView.nativeAdd(tagNativeLinkViewPtr, cacheItemIndextag);
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gidon.myrealm.Model.Url.class);
        long tableNativePtr = table.getNativePtr();
        UrlColumnInfo columnInfo = (UrlColumnInfo) realm.schema.getColumnInfo(com.example.gidon.myrealm.Model.Url.class);
        com.example.gidon.myrealm.Model.Url object = null;
        while (objects.hasNext()) {
            object = (com.example.gidon.myrealm.Model.Url) objects.next();
            if(!cache.containsKey(object)) {
                if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                    cache.put(object, ((RealmObjectProxy)object).realmGet$proxyState().getRow$realm().getIndex());
                    continue;
                }
                long rowIndex = Table.nativeAddEmptyRow(tableNativePtr, 1);
                cache.put(object, rowIndex);
                String realmGet$Title = ((UrlRealmProxyInterface)object).realmGet$Title();
                if (realmGet$Title != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.TitleIndex, rowIndex, realmGet$Title, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.TitleIndex, rowIndex, false);
                }
                String realmGet$Description = ((UrlRealmProxyInterface)object).realmGet$Description();
                if (realmGet$Description != null) {
                    Table.nativeSetString(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, realmGet$Description, false);
                } else {
                    Table.nativeSetNull(tableNativePtr, columnInfo.DescriptionIndex, rowIndex, false);
                }

                long tagNativeLinkViewPtr = Table.nativeGetLinkView(tableNativePtr, columnInfo.tagIndex, rowIndex);
                LinkView.nativeClear(tagNativeLinkViewPtr);
                RealmList<com.example.gidon.myrealm.Model.Tag> tagList = ((UrlRealmProxyInterface) object).realmGet$tag();
                if (tagList != null) {
                    for (com.example.gidon.myrealm.Model.Tag tagItem : tagList) {
                        Long cacheItemIndextag = cache.get(tagItem);
                        if (cacheItemIndextag == null) {
                            cacheItemIndextag = TagRealmProxy.insertOrUpdate(realm, tagItem, cache);
                        }
                        LinkView.nativeAdd(tagNativeLinkViewPtr, cacheItemIndextag);
                    }
                }

            }
        }
    }

    public static com.example.gidon.myrealm.Model.Url createDetachedCopy(com.example.gidon.myrealm.Model.Url realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.gidon.myrealm.Model.Url unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.gidon.myrealm.Model.Url)cachedObject.object;
            } else {
                unmanagedObject = (com.example.gidon.myrealm.Model.Url)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new com.example.gidon.myrealm.Model.Url();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        }
        ((UrlRealmProxyInterface) unmanagedObject).realmSet$Title(((UrlRealmProxyInterface) realmObject).realmGet$Title());
        ((UrlRealmProxyInterface) unmanagedObject).realmSet$Description(((UrlRealmProxyInterface) realmObject).realmGet$Description());

        // Deep copy of tag
        if (currentDepth == maxDepth) {
            ((UrlRealmProxyInterface) unmanagedObject).realmSet$tag(null);
        } else {
            RealmList<com.example.gidon.myrealm.Model.Tag> managedtagList = ((UrlRealmProxyInterface) realmObject).realmGet$tag();
            RealmList<com.example.gidon.myrealm.Model.Tag> unmanagedtagList = new RealmList<com.example.gidon.myrealm.Model.Tag>();
            ((UrlRealmProxyInterface) unmanagedObject).realmSet$tag(unmanagedtagList);
            int nextDepth = currentDepth + 1;
            int size = managedtagList.size();
            for (int i = 0; i < size; i++) {
                com.example.gidon.myrealm.Model.Tag item = TagRealmProxy.createDetachedCopy(managedtagList.get(i), nextDepth, maxDepth, cache);
                unmanagedtagList.add(item);
            }
        }
        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Url = proxy[");
        stringBuilder.append("{Title:");
        stringBuilder.append(realmGet$Title() != null ? realmGet$Title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{Description:");
        stringBuilder.append(realmGet$Description() != null ? realmGet$Description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tag:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$tag().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlRealmProxy aUrl = (UrlRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUrl.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUrl.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUrl.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
