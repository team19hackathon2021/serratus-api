package io.serratus.api.enus.base;

import io.serratus.api.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Long;
import java.util.Locale;
import java.util.Map;
import io.serratus.api.enus.request.api.ApiRequest;
import java.time.ZoneOffset;
import io.serratus.api.enus.java.ZonedDateTimeDeserializer;
import java.math.RoundingMode;
import java.math.MathContext;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import io.vertx.core.Future;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import java.time.OffsetDateTime;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import io.serratus.api.enus.java.LocalDateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.serratus.api.enus.config.ConfigKeys;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import java.lang.String;
import org.slf4j.Logger;
import io.serratus.api.enus.writer.AllWriter;
import io.vertx.core.Promise;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import io.serratus.api.enus.base.BaseModel;
import io.serratus.api.enus.java.ZonedDateTimeSerializer;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import io.serratus.api.enus.wrap.Wrap;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;

/**
 **/

public abstract class BaseModelGen<DEV> {
    public static final String VAR_userId = "userId";
    protected static final Logger LOG = LoggerFactory.getLogger(BaseModel.class);
    public Wrap<ZonedDateTime> modifiedWrap = new Wrap<ZonedDateTime>().var("modified").o(modified);
    public Wrap<Boolean> archivedWrap = new Wrap<Boolean>().var("archived").o(archived);
    public Wrap<Boolean> deletedWrap = new Wrap<Boolean>().var("deleted").o(deleted);
    public Wrap<String> classCanonicalNameWrap = new Wrap<String>().var("classCanonicalName").o(classCanonicalName);
    public Wrap<String> classSimpleNameWrap = new Wrap<String>().var("classSimpleName").o(classSimpleName);
    public Wrap<String> sessionIdWrap = new Wrap<String>().var("sessionId").o(sessionId);
    public Wrap<Long> userKeyWrap = new Wrap<Long>().var("userKey").o(userKey);
    public Wrap<List<String>> savesWrap = new Wrap<List<String>>().var("saves").o(saves);
    public Wrap<String> objectTitleWrap = new Wrap<String>().var("objectTitle").o(objectTitle);
    public Wrap<String> objectIdWrap = new Wrap<String>().var("objectId").o(objectId);
    public Wrap<String> objectNameVarWrap = new Wrap<String>().var("objectNameVar").o(objectNameVar);
    public Wrap<String> objectSuggestWrap = new Wrap<String>().var("objectSuggest").o(objectSuggest);
    public Wrap<String> objectTextWrap = new Wrap<String>().var("objectText").o(objectText);
    protected boolean alreadyInitializedBaseModel = false;
    protected String userId;
    public Wrap<String> userIdWrap = new Wrap<String>().var("userId").o(userId);
    protected SiteRequestEnUS siteRequest_;
    public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().var("siteRequest_").o(siteRequest_);
    protected Long pk;
    public Wrap<Long> pkWrap = new Wrap<Long>().var("pk").o(pk);
    protected String inheritPk;
    public Wrap<String> inheritPkWrap = new Wrap<String>().var("inheritPk").o(inheritPk);
    protected String id;
    public Wrap<String> idWrap = new Wrap<String>().var("id").o(id);
    protected ZonedDateTime created;
    public Wrap<ZonedDateTime> createdWrap = new Wrap<ZonedDateTime>().var("created").o(created);

    public static String staticSetUserId(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrUserId(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrStrUserId(SiteRequestEnUS siteRequest_, String o) {
        return o == null ? null : o.toString();
    }

    public static String staticSolrFqUserId(SiteRequestEnUS siteRequest_, String o) {
        return BaseModel.staticSolrStrUserId(siteRequest_, BaseModel.staticSolrUserId(siteRequest_, BaseModel.staticSetUserId(siteRequest_, o)));
    }

    public static SiteRequestEnUS staticSetSiteRequest_(SiteRequestEnUS siteRequest_, String o) {
        return null;
    }

    public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
        if(NumberUtils.isParsable(o))
            return Long.parseLong(o);
        return null;
    }

    public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
        return o;
    }

    public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
        return o == null ? null : o.toString();
    }

    public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
        return BaseModel.staticSolrStrPk(siteRequest_, BaseModel.staticSolrPk(siteRequest_, BaseModel.staticSetPk(siteRequest_, o)));
    }

    public static String staticSetInheritPk(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrInheritPk(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrStrInheritPk(SiteRequestEnUS siteRequest_, String o) {
        return o == null ? null : o.toString();
    }

    public static String staticSolrFqInheritPk(SiteRequestEnUS siteRequest_, String o) {
        return BaseModel.staticSolrStrInheritPk(siteRequest_, BaseModel.staticSolrInheritPk(siteRequest_, BaseModel.staticSetInheritPk(siteRequest_, o)));
    }

    public static String staticSetId(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrId(SiteRequestEnUS siteRequest_, String o) {
        return o;
    }

    public static String staticSolrStrId(SiteRequestEnUS siteRequest_, String o) {
        return o == null ? null : o.toString();
    }

    public static String staticSolrFqId(SiteRequestEnUS siteRequest_, String o) {
        return BaseModel.staticSolrStrId(siteRequest_, BaseModel.staticSolrId(siteRequest_, BaseModel.staticSetId(siteRequest_, o)));
    }

    public Date solrCreated() {
        return BaseModel.staticSolrCreated(siteRequest_, created);
    }

    public String strCreated() {
        return created == null ? "" : created.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
    }

    public String jsonCreated() {
        return created == null ? "" : created.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String htmTooltipCreated() {
        return null;
    }

    public String htmCreated() {
        return created == null ? "" : StringEscapeUtils.escapeHtml4(strCreated());
    }

    public Date solrModified() {
        return BaseModel.staticSolrModified(siteRequest_, modified);
    }

    public String strModified() {
        return modified == null ? "" : modified.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
    }

    public OffsetDateTime sqlModified() {
        return modified == null ? null : modified.toOffsetDateTime();
    }

    public String jsonModified() {
        return modified == null ? "" : modified.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String htmTooltipModified() {
        return null;
    }

    public String htmModified() {
        return modified == null ? "" : StringEscapeUtils.escapeHtml4(strModified());
    }

    public Boolean solrArchived() {
        return BaseModel.staticSolrArchived(siteRequest_, archived);
    }

    public String strArchived() {
        return archived == null ? "" : archived.toString();
    }

    public String jsonArchived() {
        return archived == null ? "" : archived.toString();
    }

    public String htmTooltipArchived() {
        return null;
    }

    public String htmArchived() {
        return archived == null ? "" : StringEscapeUtils.escapeHtml4(strArchived());
    }

    public Boolean solrDeleted() {
        return BaseModel.staticSolrDeleted(siteRequest_, deleted);
    }

    public String strDeleted() {
        return deleted == null ? "" : deleted.toString();
    }

    public String jsonDeleted() {
        return deleted == null ? "" : deleted.toString();
    }

    public String htmTooltipDeleted() {
        return null;
    }

    public String htmDeleted() {
        return deleted == null ? "" : StringEscapeUtils.escapeHtml4(strDeleted());
    }

    public String solrClassCanonicalName() {
        return BaseModel.staticSolrClassCanonicalName(siteRequest_, classCanonicalName);
    }

    public String strClassCanonicalName() {
        return classCanonicalName == null ? "" : classCanonicalName;
    }

    public String sqlClassCanonicalName() {
        return classCanonicalName;
    }

    public String jsonClassCanonicalName() {
        return classCanonicalName == null ? "" : classCanonicalName;
    }

    public String htmTooltipClassCanonicalName() {
        return null;
    }

    public String htmClassCanonicalName() {
        return classCanonicalName == null ? "" : StringEscapeUtils.escapeHtml4(strClassCanonicalName());
    }

    public String solrClassSimpleName() {
        return BaseModel.staticSolrClassSimpleName(siteRequest_, classSimpleName);
    }

    public String strClassSimpleName() {
        return classSimpleName == null ? "" : classSimpleName;
    }

    public String sqlClassSimpleName() {
        return classSimpleName;
    }

    public String jsonClassSimpleName() {
        return classSimpleName == null ? "" : classSimpleName;
    }

    public String htmTooltipClassSimpleName() {
        return null;
    }

    public String htmClassSimpleName() {
        return classSimpleName == null ? "" : StringEscapeUtils.escapeHtml4(strClassSimpleName());
    }

    public String solrSessionId() {
        return BaseModel.staticSolrSessionId(siteRequest_, sessionId);
    }

    public String strSessionId() {
        return sessionId == null ? "" : sessionId;
    }

    public String jsonSessionId() {
        return sessionId == null ? "" : sessionId;
    }

    public String htmTooltipSessionId() {
        return null;
    }

    public String htmSessionId() {
        return sessionId == null ? "" : StringEscapeUtils.escapeHtml4(strSessionId());
    }

    public String solrUserId() {
        return BaseModel.staticSolrUserId(siteRequest_, userId);
    }

    public String strUserId() {
        return userId == null ? "" : userId;
    }

    public String jsonUserId() {
        return userId == null ? "" : userId;
    }

    public String htmTooltipUserId() {
        return null;
    }

    public Long solrPk() {
        return BaseModel.staticSolrPk(siteRequest_, pk);
    }

    public String strPk() {
        return pk == null ? "" : pk.toString();
    }

    public Long sqlPk() {
        return pk;
    }

    public String jsonPk() {
        return pk == null ? "" : pk.toString();
    }

    public String htmTooltipPk() {
        return null;
    }

    public String htmPk() {
        return pk == null ? "" : StringEscapeUtils.escapeHtml4(strPk());
    }

    public String solrInheritPk() {
        return BaseModel.staticSolrInheritPk(siteRequest_, inheritPk);
    }

    public String strInheritPk() {
        return inheritPk == null ? "" : inheritPk;
    }

    public String jsonInheritPk() {
        return inheritPk == null ? "" : inheritPk;
    }

    public String htmTooltipInheritPk() {
        return null;
    }

    public String htmInheritPk() {
        return inheritPk == null ? "" : StringEscapeUtils.escapeHtml4(strInheritPk());
    }

    public String solrId() {
        return BaseModel.staticSolrId(siteRequest_, id);
    }

    public String strId() {
        return id == null ? "" : id;
    }

    public String sqlId() {
        return id;
    }

    public String jsonId() {
        return id == null ? "" : id;
    }

    public String htmTooltipId() {
        return null;
    }

    public String htmId() {
        return id == null ? "" : StringEscapeUtils.escapeHtml4(strId());
    }

    public String htmUserId() {
        return userId == null ? "" : StringEscapeUtils.escapeHtml4(strUserId());
    }

    public Long solrUserKey() {
        return BaseModel.staticSolrUserKey(siteRequest_, userKey);
    }

    public String strUserKey() {
        return userKey == null ? "" : userKey.toString();
    }

    public String jsonUserKey() {
        return userKey == null ? "" : userKey.toString();
    }

    public String htmTooltipUserKey() {
        return null;
    }

    public String htmUserKey() {
        return userKey == null ? "" : StringEscapeUtils.escapeHtml4(strUserKey());
    }

    public List<String> solrSaves() {
        List<String> l = new ArrayList<String>();
        for(String o : saves) {
            l.add(BaseModel.staticSolrSaves(siteRequest_, o));
        }
        return l;
    }

    public String strSaves() {
        return saves == null ? "" : saves.toString();
    }

    public List<String> sqlSaves() {
        return saves;
    }

    public String jsonSaves() {
        return saves == null ? "" : saves.toString();
    }

    public String htmTooltipSaves() {
        return null;
    }

    public String htmSaves() {
        return saves == null ? "" : StringEscapeUtils.escapeHtml4(strSaves());
    }

    public String solrObjectTitle() {
        return BaseModel.staticSolrObjectTitle(siteRequest_, objectTitle);
    }

    public String strObjectTitle() {
        return objectTitle == null ? "" : objectTitle;
    }

    public String sqlObjectTitle() {
        return objectTitle;
    }

    public String jsonObjectTitle() {
        return objectTitle == null ? "" : objectTitle;
    }

    public String htmTooltipObjectTitle() {
        return null;
    }

    public String htmObjectTitle() {
        return objectTitle == null ? "" : StringEscapeUtils.escapeHtml4(strObjectTitle());
    }

    public String solrObjectId() {
        return BaseModel.staticSolrObjectId(siteRequest_, objectId);
    }

    public String strObjectId() {
        return objectId == null ? "" : objectId;
    }

    public String sqlObjectId() {
        return objectId;
    }

    public String jsonObjectId() {
        return objectId == null ? "" : objectId;
    }

    public String htmTooltipObjectId() {
        return null;
    }

    public String htmObjectId() {
        return objectId == null ? "" : StringEscapeUtils.escapeHtml4(strObjectId());
    }

    public String solrObjectNameVar() {
        return BaseModel.staticSolrObjectNameVar(siteRequest_, objectNameVar);
    }

    public String strObjectNameVar() {
        return objectNameVar == null ? "" : objectNameVar;
    }

    public String sqlObjectNameVar() {
        return objectNameVar;
    }

    public String jsonObjectNameVar() {
        return objectNameVar == null ? "" : objectNameVar;
    }

    public String htmTooltipObjectNameVar() {
        return null;
    }

    public String htmObjectNameVar() {
        return objectNameVar == null ? "" : StringEscapeUtils.escapeHtml4(strObjectNameVar());
    }

    public String solrObjectSuggest() {
        return BaseModel.staticSolrObjectSuggest(siteRequest_, objectSuggest);
    }

    public String strObjectSuggest() {
        return objectSuggest == null ? "" : objectSuggest;
    }

    public String sqlObjectSuggest() {
        return objectSuggest;
    }

    public String jsonObjectSuggest() {
        return objectSuggest == null ? "" : objectSuggest;
    }

    public String htmTooltipObjectSuggest() {
        return null;
    }

    public String htmObjectSuggest() {
        return objectSuggest == null ? "" : StringEscapeUtils.escapeHtml4(strObjectSuggest());
    }

    public String solrObjectText() {
        return BaseModel.staticSolrObjectText(siteRequest_, objectText);
    }

    public String strObjectText() {
        return objectText == null ? "" : objectText;
    }

    public String sqlObjectText() {
        return objectText;
    }

    public String jsonObjectText() {
        return objectText == null ? "" : objectText;
    }

    public String htmTooltipObjectText() {
        return null;
    }

    public String htmObjectText() {
        return objectText == null ? "" : StringEscapeUtils.escapeHtml4(strObjectText());
    }

    public String solrPageUrlId() {
        return BaseModel.staticSolrPageUrlId(siteRequest_, pageUrlId);
    }

    public String strPageUrlId() {
        return pageUrlId == null ? "" : pageUrlId;
    }

    public String sqlPageUrlId() {
        return pageUrlId;
    }

    public String jsonPageUrlId() {
        return pageUrlId == null ? "" : pageUrlId;
    }

    public String htmTooltipPageUrlId() {
        return null;
    }

    public String htmPageUrlId() {
        return pageUrlId == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlId());
    }

    public String solrPageUrlPk() {
        return BaseModel.staticSolrPageUrlPk(siteRequest_, pageUrlPk);
    }

    public String strPageUrlPk() {
        return pageUrlPk == null ? "" : pageUrlPk;
    }

    public String sqlPageUrlPk() {
        return pageUrlPk;
    }

    public String jsonPageUrlPk() {
        return pageUrlPk == null ? "" : pageUrlPk;
    }

    public String htmTooltipPageUrlPk() {
        return null;
    }

    public String htmPageUrlPk() {
        return pageUrlPk == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlPk());
    }

    public String solrPageUrlApi() {
        return BaseModel.staticSolrPageUrlApi(siteRequest_, pageUrlApi);
    }

    public String strPageUrlApi() {
        return pageUrlApi == null ? "" : pageUrlApi;
    }

    public String sqlPageUrlApi() {
        return pageUrlApi;
    }

    public String jsonPageUrlApi() {
        return pageUrlApi == null ? "" : pageUrlApi;
    }

    public String htmTooltipPageUrlApi() {
        return null;
    }

    public String htmPageUrlApi() {
        return pageUrlApi == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlApi());
    }

    public String solrPageH1() {
        return BaseModel.staticSolrPageH1(siteRequest_, pageH1);
    }

    public String strPageH1() {
        return pageH1 == null ? "" : pageH1;
    }

    public String sqlPageH1() {
        return pageH1;
    }

    public String jsonPageH1() {
        return pageH1 == null ? "" : pageH1;
    }

    public String htmTooltipPageH1() {
        return null;
    }

    public String htmPageH1() {
        return pageH1 == null ? "" : StringEscapeUtils.escapeHtml4(strPageH1());
    }

    public boolean attributeForClass(String var, Object val) {
        String[] vars = StringUtils.split(var, ".");
        Object o = null;
        for(String v : vars) {
            if(o == null)
                o = attributeBaseModel(v, val);
            else if(o instanceof BaseModel) {
                BaseModel baseModel = (BaseModel)o;
                o = baseModel.attributeForClass(v, val);
            }
        }
        return o != null;
    }

    public Object attributeBaseModel(String var, Object val) {
        BaseModel oBaseModel = (BaseModel)this;
        switch(var) {
            default:
                return null;
        }
    }

    public boolean defineForClass(String var, String val) {
        String[] vars = StringUtils.split(var, ".");
        Object o = null;
        if(val != null) {
            for(String v : vars) {
                if(o == null)
                    o = defineBaseModel(v, val);
                else if(o instanceof BaseModel) {
                    BaseModel oBaseModel = (BaseModel)o;
                    o = oBaseModel.defineForClass(v, val);
                }
            }
        }
        return o != null;
    }

    public Object defineBaseModel(String var, String val) {
        switch(var.toLowerCase()) {
            case "inheritpk":
                if(val != null)
                    setInheritPk(val);
                saves.add("inheritPk");
                return val;
            case "created":
                if(val != null)
                    setCreated(val);
                saves.add("created");
                return val;
            case "archived":
                if(val != null)
                    setArchived(val);
                saves.add("archived");
                return val;
            case "deleted":
                if(val != null)
                    setDeleted(val);
                saves.add("deleted");
                return val;
            case "userid":
                if(val != null)
                    setUserId(val);
                saves.add("userId");
                return val;
            case "userkey":
                if(val != null)
                    setUserKey(val);
                saves.add("userKey");
                return val;
            default:
                return null;
        }
    }

    public int hashCode() {
        return Objects.hash(pk, inheritPk, id, created, modified, archived, deleted, classCanonicalName, classSimpleName, sessionId, userId, userKey, saves, objectTitle, objectId, objectSuggest, objectText, pageUrlId, pageUrlPk);
    }

    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof BaseModel))
            return false;
        BaseModel that = (BaseModel)o;
        return Objects.equals( pk, that.pk )
                && Objects.equals( inheritPk, that.inheritPk )
                && Objects.equals( id, that.id )
                && Objects.equals( created, that.created )
                && Objects.equals( modified, that.modified )
                && Objects.equals( archived, that.archived )
                && Objects.equals( deleted, that.deleted )
                && Objects.equals( classCanonicalName, that.classCanonicalName )
                && Objects.equals( classSimpleName, that.classSimpleName )
                && Objects.equals( sessionId, that.sessionId )
                && Objects.equals( userId, that.userId )
                && Objects.equals( userKey, that.userKey )
                && Objects.equals( saves, that.saves )
                && Objects.equals( objectTitle, that.objectTitle )
                && Objects.equals( objectId, that.objectId )
                && Objects.equals( objectSuggest, that.objectSuggest )
                && Objects.equals( objectText, that.objectText )
                && Objects.equals( pageUrlId, that.pageUrlId )
                && Objects.equals( pageUrlPk, that.pageUrlPk );
    }

    protected abstract void _userId(Wrap<String> c) {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String o) {
        this.userId = BaseModel.staticSetUserId(siteRequest_, o);
    }

    protected BaseModel userIdInit() {
        Wrap<String> userIdWrap = new Wrap<String>().var("userId");
        if(userId == null) {
            _userId(userIdWrap);
            setUserId(userIdWrap.o);
        }
        return (BaseModel)this;
    }

    public String sqlUserId() {
        return userId;
    }

    protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> w) {}

    public SiteRequestEnUS getSiteRequest_() {
        return siteRequest_;
    }

    public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
        this.siteRequest_ = siteRequest_;
    }

    protected BaseModel siteRequest_Init() {
        Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().var("siteRequest_");
        if(siteRequest_ == null) {
            _siteRequest_(siteRequest_Wrap);
            setSiteRequest_(siteRequest_Wrap.o);
        }
        return (BaseModel)this;
    }

    protected abstract void _pk(Wrap<Long> w) {}

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public void setPk(String o) {
        this.pk = BaseModel.staticSetPk(siteRequest_, o);
    }

    protected BaseModel pkInit() {
        Wrap<Long> pkWrap = new Wrap<Long>().var("pk");
        if(pk == null) {
            _pk(pkWrap);
            setPk(pkWrap.o);
        }
        return (BaseModel)this;
    }

    protected abstract void _inheritPk(Wrap<String> w) {}

    public String getInheritPk() {
        return inheritPk;
    }

    public void setInheritPk(String o) {
        this.inheritPk = BaseModel.staticSetInheritPk(siteRequest_, o);
    }

    protected BaseModel inheritPkInit() {
        Wrap<String> inheritPkWrap = new Wrap<String>().var("inheritPk");
        if(inheritPk == null) {
            _inheritPk(inheritPkWrap);
            setInheritPk(inheritPkWrap.o);
        }
        return (BaseModel)this;
    }

    public String sqlInheritPk() {
        return inheritPk;
    }

    protected abstract void _id(Wrap<String> w) {}

    public String getId() {
        return id;
    }

    public void setId(String o) {
        this.id = BaseModel.staticSetId(siteRequest_, o);
    }

    protected BaseModel idInit() {
        Wrap<String> idWrap = new Wrap<String>().var("id");
        if(id == null) {
            _id(idWrap);
            setId(idWrap.o);
        }
        return (BaseModel)this;
    }

    protected abstract void _created(Wrap<ZonedDateTime> w) {}


}