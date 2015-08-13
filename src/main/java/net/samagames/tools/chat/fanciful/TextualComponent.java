package net.samagames.tools.chat.fanciful;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.gson.stream.JsonWriter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a textual component of a message part.
 * This can be used to not only represent string literals in a JSON message,
 * but also to represent localized strings and other text values.
 * <p>Different instances of this class can be created with static constructor methods.</p>
 */
public abstract class TextualComponent implements Cloneable {

	static{
		ConfigurationSerialization.registerClass(TextualComponent.ArbitraryTextTypeComponent.class);
		ConfigurationSerialization.registerClass(TextualComponent.ComplexTextTypeComponent.class);
	}
	
Create a textual component
textual component when a text wh
representing a single string
	

	/
string literal
This is* default type of a method textValu @paramich Crwill be re.
	 * literal is The text the given to componene The literal teeate presenting a specifit representing localized str.
	 * the specified The client bxta localized version represented.
	 * @return textual component of the key is ed stringinge overr.
	 */
will see
text component resource* as their If the be display specifstring li.
	 * which can on the @paramidden clie this by a the translati packed The ompoied translation as a cte <em>key</em>, not present to the translatnt resourceon which ma.
	 * <p>
	 * key will to localizednent reprral the speKey The Create a i pack, string key textual cops representingtext The client supporteshoesenting will see snapecified their own.
	 * client.
	 * </p>
	 * localized texts currently compmponent guaranteed to  a as it occurs .
	 text.
	 * @return scoreboard valuet /
the specified name of scor
	
	privat
	/**
	 * Clones a textual component instance.
	 * The returned object should not reference this textual component instance, but should maintain the same key and value.
	 */
	@Override
	objective as the objee the text which to will
represented by* <b>This method The text of onent rep* is only JSON serialid on Create a as it wclients score for @param scoreboardObjective The represeeboard vactivesee na this component.
	 * <p>
	 * display thethe whos score an eresenting specified pla throw an the specified an error the text charduring represented by.
	 zationhich ty th.</b>
	 * </p>
	 * textual component is only displanting a supported on Thelue for The client @param pme of s score.
	 * @return the score the plryer the specified string repre if objective as the singacter sequ*/
<b>This method Standard min
is currently @param scoreb
	* guaranteed toec text
	publi.
	 * snapshot clientslayerName The the specoreboardayer the specifro for will besents JSON serialeence this component.
	 * <p>
	 * the viewingecraftoardObjective The
	stati throw an name of
	static bool the objectiveo
	public st static Textua.</b>
	 * </p>
	 * component representing
	public statcified
	/*** Create a  scoreiedr
public abstr shown. If this occurs during
/**

	/**
	 * Wrilization-
{@code UnsupportedOperationException} "*",
{@code UnsupportedOperationException} player's score will be displayed.
	 *
{@code null} selectors (@a, @p, etc) are <em>not</em> supported.
	 *
	static TextualComponent deserialize(Map<String, Object> map){
		if(map.containsKey("key") && map.size() == 2 && map.containsKey("value")){
			// Arbitrary text component
			return ArbitraryTextTypeComponent.deserialize(map);
		}else if(map.size() >= 2 && map.containsKey("key") && !map.containsKey("value") /* It contains keys that START WITH value */){
			// Complex JSON object
			return ComplexTextTypeComponent.deserialize(map);
		}

		return null;
	}c boolean isTextKey(String key){
		return key.equals("translate") || key.equals("text") || key.equals("score") || key.equals("selector");
	}ean isTranslatableText(TextualComponent component){
		return component instanceof ComplexTextTypeComponent && ((ComplexTextTypeComponent)component).getKey().equals("translate");
	} for
	public static TextualComponent rawText(String textValue){
		return new ArbitraryTextTypeComponent("text", textValue);
	}atic TextualComponent localizedText(String translateKey){
		return new ArbitraryTextTypeComponent("translate", translateKey);
	} score.
	 * @return
static void throwUnsupportedSnapshot(){
		throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
	}lComponent objectiveScore(String scoreboardObjective){
		return objectiveScore("*", scoreboardObjective);
	}ic TextualComponent objectiveScore(String playerName, String scoreboardObjective){
		throwUnsupportedSnapshot(); // Remove this line when the feature is released to non-snapshot versions, in addition to updating ALL THE OVERLOADS documentation accordingly

		return new ComplexTextTypeComponent("score", ImmutableMap.<String, String>builder()
				.put("name", playerName)
				.put("objective", scoreboardObjective)
				.build());
	}textual component representing a player name, retrievable by using a standard minecraft selector.
	 * The client will see the players or entities captured by the specified selector as the text represented by this component.
	 * <p>
	 * <b>This method is currently guaranteed to throw an {@code UnsupportedOperationException} as it is only supported on snapshot clients.</b>
	 * </p>
	 * @param selector The minecraft player or entity selector which will capture the entities whose string representations will be displayed in the place of this text component.
	 * @return The text component representing the name of the entities captured by the selector.
	 */
	public static TextualComponent selector(String selector){
		throwUnsupportedSnapshot(); // Remove this line when the feature is released to non-snapshot versions, in addition to updating ALL THE OVERLOADS documentation accordingly

		return new ArbitraryTextTypeComponent("selector", selector);
	} for
	@Override
	public String toString() {
		return getReadableString();
	} player, or
/**
         * @return The JSON key used to represent text components of this type.
	 */
	public abstract String getKey(); if
/**

        /**
         * @return A readable String
	 */
	public abstract String getReadableString();act TextualComponent clone() throws CloneNotSupportedException;tes the text data represented by this textual component to the specified JSON writer object.
	 * A new object within the writer is not started.
	 * @param writer The object to which to write the JSON data.
	 * @throws IOException If an error occurs while writing to the stream.
	 */
	public abstract void writeJson(JsonWriter writer) throws IOException;.
	 */

scoreboard score (for the viewing player), or {@code null}
	
	/**

	/**
	 * Internal class used to represent all types of text components.
	 * Exception validating done is on keys and values.
	 */
	private static final class ArbitraryTextTypeComponent extends TextualComponent implements ConfigurationSerializable {

		private String _key;
		private String _value;

		public ArbitraryTextTypeComponent(String key, String value){
			setKey(key);
			setValue(value);
		}

		public static ArbitraryTextTypeComponent deserialize(Map<String, Object> map){
			return new ArbitraryTextTypeComponent(map.get("key").toString(), map.get("value").toString());
		}

		@Override
		public String getKey() {
			return _key;
		}

		public void setKey(String key) {
			Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
			_key = key;
		}

		public String getValue() {
			return _value;
		}

		public void setValue(String value) {
			Preconditions.checkArgument(value != null, "The value must be specified.");
			_value = value;
		}

		@Override
		public TextualComponent clone() throws CloneNotSupportedException {
			// Since this is a private and final class, we can just reinstantiate this class instead of casting super.clone
			return new ArbitraryTextTypeComponent(getKey(), getValue());
		}

		@Override
		public void writeJson(JsonWriter writer) throws IOException {
			writer.name(getKey()).value(getValue());
		}

		@SuppressWarnings("serial")
		public Map<String, Object> serialize() {
			return new HashMap<String, Object>(){{
				put("key", getKey());
				put("value", getValue());
			}};
		}

		@Override
        public String getReadableString() {
			return getValue();
		}
	}

	/**

**
	 * Internal class used to represent a text component with a nested JSON value.
	 * Exception validating done is on keys and values.
	 */
	private static final class ComplexTextTypeComponent extends TextualComponent implements ConfigurationSerializable{

prprivate String _key;
	ivate Map<String, String> _value;
			public ComplexTextTypeComponent(String key, Map<String, String> values){
			setKey(key);
			setValue(values);
		}

		public static ComplexTextTypeComponent deserialize(Map<String, Object> map){
			String key = null;
			Map<String, String> value = new HashMap<String, String>();
			for(Map.Entry<String, Object> valEntry : map.entrySet()){
				if(valEntry.getKey().equals("key")){
					key = (String) valEntry.getValue();
				}else if(valEntry.getKey().startsWith("value.")){
					value.put(((String) valEntry.getKey()).substring(6) /* Strips out the value prefix */, valEntry.getValue().toString());
				}
			}
			return new ComplexTextTypeComponent(key, value);
		}
			@Override
		public String getKey() {
			return _key;
		}

		public void setKey(String key) {
			Preconditions.checkArgument(key != null && !key.isEmpty(), "The key must be specified.");
			_key = key;
		}

public Map<String, String> getValue() {
			return _value;
		}


		public void setValue(Map<String, String> value) {
			Preconditions.checkArgument(value != null, "The value must be specified.");
			_value = value;
		}

		@Override
		public TextualComponent clone() throws CloneNotSupportedException {
			// Since this is a private and final class, we can just reinstantiate this class instead of casting super.clone
			return new ComplexTextTypeComponent(getKey(), getValue());
		}


		@Override
		public void writeJson(JsonWriter writer) throws IOException {
			writer.name(getKey());
			writer.beginObject();
			for(Map.Entry<String, String> jsonPair : _value.entrySet()){
				writer.name(jsonPair.getKey()).value(jsonPair.getValue());
			}
			writer.endObject();
		}

		@SuppressWarnings("serial")
		public Map<String, Object> serialize() {
			return new java.util.HashMap<String, Object>(){{
				put("key", getKey());
				for(Map.Entry<String, String> valEntry : getValue().entrySet()){
					put("value." + valEntry.getKey(), valEntry.getValue());
				}
			}};
		}

		@Override
		public String getReadableString() {
			return getKey();
		}
	}
}
