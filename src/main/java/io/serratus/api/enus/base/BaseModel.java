package io.serratus.api.enus.base;

import java.text.Normalizer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import io.serratus.api.enus.config.ConfigKeys;
import io.serratus.api.enus.request.SiteRequestEnUS;
import io.serratus.api.enus.wrap.Wrap;

/**
 * Indexed: true
 **/

public class BaseModel extends BaseModelGen<Object> {
    protected void _siteRequest_(Wrap<SiteRequestEnUS> w) {}
    protected void _pk(Wrap<Long> w) {}
    protected void _inheritPk(Wrap<String> w) {}
    protected void _id(Wrap<String> w) {
        if(pk != null)
            w.o(getClass().getSimpleName() + "_" + pk.toString());
    }
    protected void _created(Wrap<ZonedDateTime> w) {}
    protected void _modified(Wrap<ZonedDateTime> w) {
        w.o(ZonedDateTime.now(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
    }
    protected void _archived(Wrap<Boolean> w) {
        w.o(false);
    }
    protected void _deleted(Wrap<Boolean> w) {
        w.o(false);
    }
    protected void _classCanonicalName(Wrap<String> w) {
        w.o(getClass().getCanonicalName());
    }
    protected void _classSimpleName(Wrap<String> w) {
        w.o(getClass().getSimpleName());
    }
    protected void _sessionId(Wrap<String> w) {
    }
    protected void _userKey(Wrap<Long> c) {
    }
    protected void _saves(List<String> l) {
    }
    protected void _objectTitle(Wrap<String> w) {
        w.o(toString());
    }
    protected void _objectId(Wrap<String> w) {
        if(objectTitle != null) {
            w.o(toId(objectTitle));
        }
        else if(pk != null){
            w.o(pk.toString());
        }
    }
    public String toId(String s) {
        if(s != null) {
            s = Normalizer.normalize(s, Normalizer.Form.NFD);
            s = StringUtils.lowerCase(s);
            s = StringUtils.trim(s);
            s = StringUtils.replacePattern(s, "\\s{1,}", "-");
            s = StringUtils.replacePattern(s, "[^\\w-]", "");
            s = StringUtils.replacePattern(s, "-{2,}", "-");
        }

        return s;
    }
    protected void _objectNameVar(Wrap<String> w) {
        if(objectId != null) {
            Class<?> cl = getClass();

            try {
                String o = toId(StringUtils.join(StringUtils.splitByCharacterTypeCamelCase((String)FieldUtils.getField(cl, cl.getSimpleName() + "_NameVar").get(this)), "-"));
                w.o(o);
            } catch (Exception e) {
                ExceptionUtils.rethrow(e);
            }
        }
    }
    protected void _objectSuggest(Wrap<String> w) {
        w.o(toString());
    }
    protected void _objectText(Wrap<String> w) {
        w.o(toString());
    }
    protected void _pageUrlId(Wrap<String> w) {
        if(objectId != null) {
            String o = siteRequest_.getConfig().getString(ConfigKeys.SITE_BASE_URL) + "/" + objectNameVar + "/" + objectId;
            w.o(o);
        }
    }
    protected void _pageUrlPk(Wrap<String> w) {
        if(pk != null) {
            String o = siteRequest_.getConfig().getString(ConfigKeys.SITE_BASE_URL) + "/" + objectNameVar + "/" + pk;
            w.o(o);
        }
    }
    protected void _pageUrlApi(Wrap<String> w) {
        if(pk != null) {
            String o = siteRequest_.getConfig().getString(ConfigKeys.SITE_BASE_URL) + "/api/" + objectNameVar + "/" + pk;
            w.o(o);
        }
    }
    protected void _pageH1(Wrap<String> w) {
        try {
            Class<?> cl = getClass();
            w.o((String)FieldUtils.getField(cl, cl.getSimpleName() + "_NameSingular").get(this) + ": " + objectTitle);
        } catch (Exception e) {
            ExceptionUtils.rethrow(e);
        }
    }
}