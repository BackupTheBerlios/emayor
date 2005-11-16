package org.emayor.webtier.shared;

/**
 *  A simple object containing a boolean value
 *  and a comment string.
 * 
 *  It is used for more return information for calling methods,
 *  which in the classic way only would return a boolean.
 *  Example:
 *  If something goes wrong, instead of throwing exceptions,
 *  the method returns isTrue=false and the reason in the comment.
 */

public class CommentedBoolean
{

public boolean isTrue = false;
public String comment = "";

public CommentedBoolean()
{
}

public CommentedBoolean( final boolean isTrue, final String comment )
{
  this.isTrue = isTrue;
  this.comment = comment;
}



} // CommentedBoolean
 
