package learn.english.parser;


import learn.english.parser.exception.ParserException;

import java.util.Set;

/**
 * Created by yaroslav on 8/6/14.
 */
public interface Parser {
    Text parse(byte[] content) throws ParserException;
}
