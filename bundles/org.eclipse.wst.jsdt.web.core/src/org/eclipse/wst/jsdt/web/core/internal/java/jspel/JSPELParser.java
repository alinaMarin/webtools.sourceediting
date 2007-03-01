/*******************************************************************************
 * Copyright (c) 2005 BEA Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BEA Systems - initial implementation
 *     
 *******************************************************************************/
/* Generated By:JJTree&JavaCC: Do not edit this line. JSPELParser.java */
package org.eclipse.wst.jsdt.web.core.internal.java.jspel;

public class JSPELParser/* @bgen(jjtree) */implements JSPELParserTreeConstants,
		JSPELParserConstants {/* @bgen(jjtree) */
	protected JJTJSPELParserState jjtree = new JJTJSPELParserState();

	void jjtreeOpenNodeScope(Node n) {
		((SimpleNode) n).setFirstToken(getToken(1));
	}

	void jjtreeCloseNodeScope(Node n) {
		((SimpleNode) n).setLastToken(getToken(0));
	}

	public static JSPELParser createParser(java.lang.String input) {
		java.io.StringReader reader = new java.io.StringReader(input);
		return new JSPELParser(reader);
	}

	public void ReInit(java.lang.String input) {
		java.io.StringReader reader = new java.io.StringReader(input);
		ReInit(reader);
	}

	final public ASTExpression Expression() throws ParseException {
		/* @bgen(jjtree) Expression */
		ASTExpression jjtn000 = new ASTExpression(JJTEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			if (jj_2_1(2147483647)) {
				ChoiceExpression();
			} else {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case STRING_LITERAL:
				case TRUE:
				case FALSE:
				case NULL:
				case LPAREN:
				case MINUS:
				case NOT1:
				case NOT2:
				case EMPTY:
				case IDENTIFIER:
					OrExpression();
					break;
				default:
					jj_la1[0] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
			}
			jjtree.closeNodeScope(jjtn000, true);
			jjtc000 = false;
			jjtreeCloseNodeScope(jjtn000);
			{
				if (true) {
					return jjtn000;
				}
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
		throw new Error("Missing return statement in function");
	}

	final public void OrExpression() throws ParseException {
		/* @bgen(jjtree) #OrExpression(> 1) */
		ASTOrExpression jjtn000 = new ASTOrExpression(JJTOREXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			AndExpression();
			label_1: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case OR1:
				case OR2:

					break;
				default:
					jj_la1[1] = jj_gen;
					break label_1;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case OR1:
					t = jj_consume_token(OR1);
					break;
				case OR2:
					t = jj_consume_token(OR2);
					break;
				default:
					jj_la1[2] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				AndExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void AndExpression() throws ParseException {
		/* @bgen(jjtree) #AndExpression(> 1) */
		ASTAndExpression jjtn000 = new ASTAndExpression(JJTANDEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			EqualityExpression();
			label_2: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AND1:
				case AND2:

					break;
				default:
					jj_la1[3] = jj_gen;
					break label_2;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case AND1:
					t = jj_consume_token(AND1);
					break;
				case AND2:
					t = jj_consume_token(AND2);
					break;
				default:
					jj_la1[4] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				EqualityExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void EqualityExpression() throws ParseException {
		/* @bgen(jjtree) #EqualityExpression(> 1) */
		ASTEqualityExpression jjtn000 = new ASTEqualityExpression(
				JJTEQUALITYEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			RelationalExpression();
			label_3: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case EQ1:
				case EQ2:
				case NEQ1:
				case NEQ2:

					break;
				default:
					jj_la1[5] = jj_gen;
					break label_3;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case EQ1:
					t = jj_consume_token(EQ1);
					break;
				case EQ2:
					t = jj_consume_token(EQ2);
					break;
				case NEQ1:
					t = jj_consume_token(NEQ1);
					break;
				case NEQ2:
					t = jj_consume_token(NEQ2);
					break;
				default:
					jj_la1[6] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				RelationalExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void RelationalExpression() throws ParseException {
		/* @bgen(jjtree) #RelationalExpression(> 1) */
		ASTRelationalExpression jjtn000 = new ASTRelationalExpression(
				JJTRELATIONALEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			AddExpression();
			label_4: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case GT1:
				case GT2:
				case LT1:
				case LT2:
				case LE1:
				case LE2:
				case GE1:
				case GE2:

					break;
				default:
					jj_la1[7] = jj_gen;
					break label_4;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case LT1:
					t = jj_consume_token(LT1);
					break;
				case LT2:
					t = jj_consume_token(LT2);
					break;
				case GT1:
					t = jj_consume_token(GT1);
					break;
				case GT2:
					t = jj_consume_token(GT2);
					break;
				case GE1:
					t = jj_consume_token(GE1);
					break;
				case GE2:
					t = jj_consume_token(GE2);
					break;
				case LE1:
					t = jj_consume_token(LE1);
					break;
				case LE2:
					t = jj_consume_token(LE2);
					break;
				default:
					jj_la1[8] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				AddExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void AddExpression() throws ParseException {
		/* @bgen(jjtree) #AddExpression(> 1) */
		ASTAddExpression jjtn000 = new ASTAddExpression(JJTADDEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			MultiplyExpression();
			label_5: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case PLUS:
				case MINUS:

					break;
				default:
					jj_la1[9] = jj_gen;
					break label_5;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case PLUS:
					t = jj_consume_token(PLUS);
					break;
				case MINUS:
					t = jj_consume_token(MINUS);
					break;
				default:
					jj_la1[10] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				MultiplyExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void MultiplyExpression() throws ParseException {
		/* @bgen(jjtree) #MultiplyExpression(> 1) */
		ASTMultiplyExpression jjtn000 = new ASTMultiplyExpression(
				JJTMULTIPLYEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t;
		try {
			UnaryExpression();
			label_6: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case MULTIPLY:
				case DIVIDE1:
				case DIVIDE2:
				case MODULUS1:
				case MODULUS2:

					break;
				default:
					jj_la1[11] = jj_gen;
					break label_6;
				}
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case MULTIPLY:
					t = jj_consume_token(MULTIPLY);
					break;
				case DIVIDE1:
					t = jj_consume_token(DIVIDE1);
					break;
				case DIVIDE2:
					t = jj_consume_token(DIVIDE2);
					break;
				case MODULUS1:
					t = jj_consume_token(MODULUS1);
					break;
				case MODULUS2:
					t = jj_consume_token(MODULUS2);
					break;
				default:
					jj_la1[12] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				jjtn000.addOperatorToken(t);
				UnaryExpression();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, jjtree.nodeArity() > 1);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void ChoiceExpression() throws ParseException {
		/* @bgen(jjtree) ChoiceExpression */
		ASTChoiceExpression jjtn000 = new ASTChoiceExpression(
				JJTCHOICEEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			OrExpression();
			jj_consume_token(COND);
			OrExpression();
			jj_consume_token(COLON);
			OrExpression();
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void UnaryExpression() throws ParseException {
		/* @bgen(jjtree) UnaryExpression */
		ASTUnaryExpression jjtn000 = new ASTUnaryExpression(JJTUNARYEXPRESSION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case MINUS:
			case NOT1:
			case NOT2:
			case EMPTY:
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case NOT1:
					jj_consume_token(NOT1);
					break;
				case NOT2:
					jj_consume_token(NOT2);
					break;
				case MINUS:
					jj_consume_token(MINUS);
					break;
				case EMPTY:
					jj_consume_token(EMPTY);
					break;
				default:
					jj_la1[13] = jj_gen;
					jj_consume_token(-1);
					throw new ParseException();
				}
				UnaryExpression();
				break;
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case STRING_LITERAL:
			case TRUE:
			case FALSE:
			case NULL:
			case LPAREN:
			case IDENTIFIER:
				Value();
				break;
			default:
				jj_la1[14] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void Value() throws ParseException {
		/* @bgen(jjtree) Value */
		ASTValue jjtn000 = new ASTValue(JJTVALUE);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			ValuePrefix();
			label_7: while (true) {
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case DOT:
				case LBRACKET:

					break;
				default:
					jj_la1[15] = jj_gen;
					break label_7;
				}
				ValueSuffix();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void ValuePrefix() throws ParseException {
		/* @bgen(jjtree) ValuePrefix */
		ASTValuePrefix jjtn000 = new ASTValuePrefix(JJTVALUEPREFIX);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case STRING_LITERAL:
			case TRUE:
			case FALSE:
			case NULL:
				Literal();
				break;
			case LPAREN:
				jj_consume_token(LPAREN);
				Expression();
				jj_consume_token(RPAREN);
				break;
			default:
				jj_la1[16] = jj_gen;
				if (jj_2_2(3)) {
					FunctionInvocation();
				} else {
					switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case IDENTIFIER:
						jj_consume_token(IDENTIFIER);
						break;
					default:
						jj_la1[17] = jj_gen;
						jj_consume_token(-1);
						throw new ParseException();
					}
				}
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void ValueSuffix() throws ParseException {
		/* @bgen(jjtree) ValueSuffix */
		ASTValueSuffix jjtn000 = new ASTValueSuffix(JJTVALUESUFFIX);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token t = null;
		try {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case DOT:
				jj_consume_token(DOT);
				switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
				case IDENTIFIER:
					t = jj_consume_token(IDENTIFIER);
					break;
				default:
					jj_la1[18] = jj_gen;

				}
				jjtree.closeNodeScope(jjtn000, true);
				jjtc000 = false;
				jjtreeCloseNodeScope(jjtn000);
				jjtn000.setPropertyNameToken(t);
				break;
			case LBRACKET:
				jj_consume_token(LBRACKET);
				Expression();
				jj_consume_token(RBRACKET);
				break;
			default:
				jj_la1[19] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void FunctionInvocation() throws ParseException {
		/* @bgen(jjtree) FunctionInvocation */
		ASTFunctionInvocation jjtn000 = new ASTFunctionInvocation(
				JJTFUNCTIONINVOCATION);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		Token prefix = null, name = null;
		try {
			prefix = jj_consume_token(IDENTIFIER);
			jj_consume_token(COLON);
			jjtn000.setFullFunctionName(prefix.image + ":");
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case IDENTIFIER:
				name = jj_consume_token(IDENTIFIER);
				break;
			default:
				jj_la1[20] = jj_gen;

			}
			if (null != prefix && null != name) {
				jjtn000.setFullFunctionName(prefix.image + ":"
						+ (null == name ? "" : name.image));
			} else if (null != name) {
				jjtn000.setFullFunctionName(name.image);
			}
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case LPAREN:
				jj_consume_token(LPAREN);
				Expression();
				label_8: while (true) {
					switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
					case COMMA:

						break;
					default:
						jj_la1[21] = jj_gen;
						break label_8;
					}
					jj_consume_token(COMMA);
					Expression();
				}
				try {
					jj_consume_token(RPAREN);
				} catch (Exception e) {

				}
				break;
			default:
				jj_la1[22] = jj_gen;

			}
		} catch (Throwable jjte000) {
			if (jjtc000) {
				jjtree.clearNodeScope(jjtn000);
				jjtc000 = false;
			} else {
				jjtree.popNode();
			}
			if (jjte000 instanceof RuntimeException) {
				{
					if (true) {
						throw (RuntimeException) jjte000;
					}
				}
			}
			if (jjte000 instanceof ParseException) {
				{
					if (true) {
						throw (ParseException) jjte000;
					}
				}
			}
			{
				if (true) {
					throw (Error) jjte000;
				}
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final public void Literal() throws ParseException {
		/* @bgen(jjtree) Literal */
		ASTLiteral jjtn000 = new ASTLiteral(JJTLITERAL);
		boolean jjtc000 = true;
		jjtree.openNodeScope(jjtn000);
		jjtreeOpenNodeScope(jjtn000);
		try {
			switch ((jj_ntk == -1) ? jj_ntk() : jj_ntk) {
			case TRUE:
				jj_consume_token(TRUE);
				break;
			case FALSE:
				jj_consume_token(FALSE);
				break;
			case INTEGER_LITERAL:
				jj_consume_token(INTEGER_LITERAL);
				break;
			case FLOATING_POINT_LITERAL:
				jj_consume_token(FLOATING_POINT_LITERAL);
				break;
			case STRING_LITERAL:
				jj_consume_token(STRING_LITERAL);
				break;
			case NULL:
				jj_consume_token(NULL);
				break;
			default:
				jj_la1[23] = jj_gen;
				jj_consume_token(-1);
				throw new ParseException();
			}
		} finally {
			if (jjtc000) {
				jjtree.closeNodeScope(jjtn000, true);
				jjtreeCloseNodeScope(jjtn000);
			}
		}
	}

	final private boolean jj_2_1(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_1();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(0, xla);
		}
	}

	final private boolean jj_2_2(int xla) {
		jj_la = xla;
		jj_lastpos = jj_scanpos = token;
		try {
			return !jj_3_2();
		} catch (LookaheadSuccess ls) {
			return true;
		} finally {
			jj_save(1, xla);
		}
	}

	final private boolean jj_3R_10() {
		if (jj_scan_token(LPAREN)) {
			return true;
		}
		return false;
	}

	final private boolean jj_3R_9() {
		if (jj_scan_token(IDENTIFIER)) {
			return true;
		}
		if (jj_scan_token(COLON)) {
			return true;
		}
		Token xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(47)) {
			jj_scanpos = xsp;
		}
		xsp = jj_scanpos;
		if (jj_3R_10()) {
			jj_scanpos = xsp;
		}
		return false;
	}

	final private boolean jj_3_2() {
		if (jj_3R_9()) {
			return true;
		}
		return false;
	}

	final private boolean jj_3_1() {
		if (jj_scan_token(COND)) {
			return true;
		}
		return false;
	}

	public JSPELParserTokenManager token_source;
	SimpleCharStream jj_input_stream;
	public Token token, jj_nt;
	private int jj_ntk;
	private Token jj_scanpos, jj_lastpos;
	private int jj_la;
	public boolean lookingAhead = false;
	// private boolean jj_semLA;
	private int jj_gen;
	final private int[] jj_la1 = new int[24];
	static private int[] jj_la1_0;
	static private int[] jj_la1_1;
	static {
		jj_la1_0();
		jj_la1_1();
	}

	private static void jj_la1_0() {
		jj_la1_0 = new int[] { 0x4001d60, 0x0, 0x0, 0x0, 0x0, 0x30c0000,
				0x30c0000, 0xf3c000, 0xf3c000, 0x0, 0x0, 0x0, 0x0, 0x0,
				0x4001d60, 0x40002000, 0x4001d60, 0x0, 0x0, 0x40002000, 0x0,
				0x10000000, 0x4000000, 0x1d60, };
	}

	private static void jj_la1_1() {
		jj_la1_1 = new int[] { 0xa182, 0x1800, 0x1800, 0x600, 0x600, 0x0, 0x0,
				0x0, 0x0, 0x3, 0x3, 0x7c, 0x7c, 0x2182, 0xa182, 0x0, 0x0,
				0x8000, 0x8000, 0x0, 0x8000, 0x0, 0x0, 0x0, };
	}

	final private JJCalls[] jj_2_rtns = new JJCalls[2];
	private boolean jj_rescan = false;
	private int jj_gc = 0;

	public JSPELParser(java.io.InputStream stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new JSPELParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	public void ReInit(java.io.InputStream stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jjtree.reset();
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	public JSPELParser(java.io.Reader stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new JSPELParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jjtree.reset();
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	public JSPELParser(JSPELParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	public void ReInit(JSPELParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jjtree.reset();
		jj_gen = 0;
		for (int i = 0; i < 24; i++) {
			jj_la1[i] = -1;
		}
		for (int i = 0; i < jj_2_rtns.length; i++) {
			jj_2_rtns[i] = new JJCalls();
		}
	}

	final private Token jj_consume_token(int kind) throws ParseException {
		Token oldToken;
		if ((oldToken = token).next != null) {
			token = token.next;
		} else {
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		if (token.kind == kind) {
			jj_gen++;
			if (++jj_gc > 100) {
				jj_gc = 0;
				for (int i = 0; i < jj_2_rtns.length; i++) {
					JJCalls c = jj_2_rtns[i];
					while (c != null) {
						if (c.gen < jj_gen) {
							c.first = null;
						}
						c = c.next;
					}
				}
			}
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	static private final class LookaheadSuccess extends java.lang.Error {
		private static final long serialVersionUID = 1L;
	}

	final private LookaheadSuccess jj_ls = new LookaheadSuccess();

	final private boolean jj_scan_token(int kind) {
		if (jj_scanpos == jj_lastpos) {
			jj_la--;
			if (jj_scanpos.next == null) {
				jj_lastpos = jj_scanpos = jj_scanpos.next = token_source
						.getNextToken();
			} else {
				jj_lastpos = jj_scanpos = jj_scanpos.next;
			}
		} else {
			jj_scanpos = jj_scanpos.next;
		}
		if (jj_rescan) {
			int i = 0;
			Token tok = token;
			while (tok != null && tok != jj_scanpos) {
				i++;
				tok = tok.next;
			}
			if (tok != null) {
				jj_add_error_token(kind, i);
			}
		}
		if (jj_scanpos.kind != kind) {
			return true;
		}
		if (jj_la == 0 && jj_scanpos == jj_lastpos) {
			throw jj_ls;
		}
		return false;
	}

	final public Token getNextToken() {
		if (token.next != null) {
			token = token.next;
		} else {
			token = token.next = token_source.getNextToken();
		}
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	final public Token getToken(int index) {
		Token t = lookingAhead ? jj_scanpos : token;
		for (int i = 0; i < index; i++) {
			if (t.next != null) {
				t = t.next;
			} else {
				t = t.next = token_source.getNextToken();
			}
		}
		return t;
	}

	final private int jj_ntk() {
		if ((jj_nt = token.next) == null) {
			return (jj_ntk = (token.next = token_source.getNextToken()).kind);
		} else {
			return (jj_ntk = jj_nt.kind);
		}
	}

	private java.util.Vector jj_expentries = new java.util.Vector();
	private int[] jj_expentry;
	private int jj_kind = -1;
	private int[] jj_lasttokens = new int[100];
	private int jj_endpos;

	private void jj_add_error_token(int kind, int pos) {
		if (pos >= 100) {
			return;
		}
		if (pos == jj_endpos + 1) {
			jj_lasttokens[jj_endpos++] = kind;
		} else if (jj_endpos != 0) {
			jj_expentry = new int[jj_endpos];
			for (int i = 0; i < jj_endpos; i++) {
				jj_expentry[i] = jj_lasttokens[i];
			}
			boolean exists = false;
			for (java.util.Enumeration e = jj_expentries.elements(); e
					.hasMoreElements();) {
				int[] oldentry = (int[]) (e.nextElement());
				if (oldentry.length == jj_expentry.length) {
					exists = true;
					for (int i = 0; i < jj_expentry.length; i++) {
						if (oldentry[i] != jj_expentry[i]) {
							exists = false;
							break;
						}
					}
					if (exists) {
						break;
					}
				}
			}
			if (!exists) {
				jj_expentries.addElement(jj_expentry);
			}
			if (pos != 0) {
				jj_lasttokens[(jj_endpos = pos) - 1] = kind;
			}
		}
	}

	public ParseException generateParseException() {
		jj_expentries.removeAllElements();
		boolean[] la1tokens = new boolean[51];
		for (int i = 0; i < 51; i++) {
			la1tokens[i] = false;
		}
		if (jj_kind >= 0) {
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 24; i++) {
			if (jj_la1[i] == jj_gen) {
				for (int j = 0; j < 32; j++) {
					if ((jj_la1_0[i] & (1 << j)) != 0) {
						la1tokens[j] = true;
					}
					if ((jj_la1_1[i] & (1 << j)) != 0) {
						la1tokens[32 + j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 51; i++) {
			if (la1tokens[i]) {
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.addElement(jj_expentry);
			}
		}
		jj_endpos = 0;
		jj_rescan_token();
		jj_add_error_token(0, 0);
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++) {
			exptokseq[i] = (int[]) jj_expentries.elementAt(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	final public void enable_tracing() {
	}

	final public void disable_tracing() {
	}

	final private void jj_rescan_token() {
		jj_rescan = true;
		for (int i = 0; i < 2; i++) {
			JJCalls p = jj_2_rtns[i];
			do {
				if (p.gen > jj_gen) {
					jj_la = p.arg;
					jj_lastpos = jj_scanpos = p.first;
					switch (i) {
					case 0:
						jj_3_1();
						break;
					case 1:
						jj_3_2();
						break;
					}
				}
				p = p.next;
			} while (p != null);
		}
		jj_rescan = false;
	}

	final private void jj_save(int index, int xla) {
		JJCalls p = jj_2_rtns[index];
		while (p.gen > jj_gen) {
			if (p.next == null) {
				p = p.next = new JJCalls();
				break;
			}
			p = p.next;
		}
		p.gen = jj_gen + xla - jj_la;
		p.first = token;
		p.arg = xla;
	}

	static final class JJCalls {
		int gen;
		Token first;
		int arg;
		JJCalls next;
	}

}
