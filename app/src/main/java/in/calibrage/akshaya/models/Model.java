package in.calibrage.akshaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {

    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;
    @SerializedName("altSpellings")
    @Expose
    private List<String> altSpellings = null;
    @SerializedName("area")
    @Expose
    private Integer area;
    @SerializedName("borders")
    @Expose
    private List<String> borders = null;
    @SerializedName("callingCodes")
    @Expose
    private List<String> callingCodes = null;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("currencies")
    @Expose
    private List<Currency> currencies = null;
    @SerializedName("demonym")
    @Expose
    private String demonym;
    @SerializedName("flagPNG")
    @Expose
    private String flagPNG;
    @SerializedName("gini")
    @Expose
    private Double gini;
    @SerializedName("languages")
    @Expose
    private List<Language> languages = null;
    @SerializedName("latlng")
    @Expose
    private List<Integer> latlng = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nativeName")
    @Expose
    private String nativeName;
    @SerializedName("numericCode")
    @Expose
    private String numericCode;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("regionalBlocs")
    @Expose
    private List<RegionalBloc> regionalBlocs = null;
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @SerializedName("timezones")
    @Expose
    private List<String> timezones = null;
    @SerializedName("topLevelDomain")
    @Expose
    private List<String> topLevelDomain = null;
    @SerializedName("translations")
    @Expose
    private Translations translations;
    @SerializedName("cioc")
    @Expose
    private String cioc;

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public String getFlagPNG() {
        return flagPNG;
    }

    public void setFlagPNG(String flagPNG) {
        this.flagPNG = flagPNG;
    }

    public Double getGini() {
        return gini;
    }

    public void setGini(Double gini) {
        this.gini = gini;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Integer> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Integer> latlng) {
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<RegionalBloc> getRegionalBlocs() {
        return regionalBlocs;
    }

    public void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {
        this.regionalBlocs = regionalBlocs;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }


    public class RegionalBloc {

        @SerializedName("acronym")
        @Expose
        private String acronym;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("otherNames")
        @Expose
        private List<String> otherNames = null;

        public String getAcronym() {
            return acronym;
        }

        public void setAcronym(String acronym) {
            this.acronym = acronym;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getOtherNames() {
            return otherNames;
        }

        public void setOtherNames(List<String> otherNames) {
            this.otherNames = otherNames;
        }

    }

    public class Translations {

        @SerializedName("br")
        @Expose
        private String br;
        @SerializedName("de")
        @Expose
        private String de;
        @SerializedName("es")
        @Expose
        private String es;
        @SerializedName("fa")
        @Expose
        private String fa;
        @SerializedName("fr")
        @Expose
        private String fr;
        @SerializedName("hr")
        @Expose
        private String hr;
        @SerializedName("it")
        @Expose
        private String it;
        @SerializedName("ja")
        @Expose
        private String ja;
        @SerializedName("nl")
        @Expose
        private String nl;
        @SerializedName("pt")
        @Expose
        private String pt;

        public String getBr() {
            return br;
        }

        public void setBr(String br) {
            this.br = br;
        }

        public String getDe() {
            return de;
        }

        public void setDe(String de) {
            this.de = de;
        }

        public String getEs() {
            return es;
        }

        public void setEs(String es) {
            this.es = es;
        }

        public String getFa() {
            return fa;
        }

        public void setFa(String fa) {
            this.fa = fa;
        }

        public String getFr() {
            return fr;
        }

        public void setFr(String fr) {
            this.fr = fr;
        }

        public String getHr() {
            return hr;
        }

        public void setHr(String hr) {
            this.hr = hr;
        }

        public String getIt() {
            return it;
        }

        public void setIt(String it) {
            this.it = it;
        }

        public String getJa() {
            return ja;
        }

        public void setJa(String ja) {
            this.ja = ja;
        }

        public String getNl() {
            return nl;
        }

        public void setNl(String nl) {
            this.nl = nl;
        }

        public String getPt() {
            return pt;
        }

        public void setPt(String pt) {
            this.pt = pt;
        }

    }

    public class Language {

        @SerializedName("iso639_1")
        @Expose
        private String iso6391;
        @SerializedName("iso639_2")
        @Expose
        private String iso6392;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("nativeName")
        @Expose
        private String nativeName;

        public String getIso6391() {
            return iso6391;
        }

        public void setIso6391(String iso6391) {
            this.iso6391 = iso6391;
        }

        public String getIso6392() {
            return iso6392;
        }

        public void setIso6392(String iso6392) {
            this.iso6392 = iso6392;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNativeName() {
            return nativeName;
        }

        public void setNativeName(String nativeName) {
            this.nativeName = nativeName;
        }

    }

    public class Currency {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("symbol")
        @Expose
        private String symbol;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

    }
}
