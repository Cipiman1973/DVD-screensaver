package Shader;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL33;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Shader {

    String vertexShaderSource="#version 330 core\n"
            + "layout (location = 0) in vec3 aPos;\n"
            + "layout (location = 1) in vec3 aColor;\n"
            + "layout (location = 2) in vec2 aTexCoord;\n"
            + "\n"
            + "out vec3 ourColor;\n"
            + "out vec2 TexCoord;\n"
            + "\n"
            +"uniform mat4 transformationMatrix;\n"
            +"\n"
            + "void main()\n"
            + "{\n"
            + "	gl_Position = vec4 (transformationMatrix * vec4(aPos,1.0));\n"
            + "	ourColor = aColor;\n"
            + "	TexCoord = vec2(aTexCoord.x, aTexCoord.y);\n"
            + "}";
    String fragmentShaderSource="#version 330 core\n"
            + "out vec4 FragColor;\n"
            + "\n"
            + "in vec3 ourColor;\n"
            + "in vec2 TexCoord;\n"
            + "\n"
            + "// texture sampler\n"
            + "uniform sampler2D texture1;\n"
            + "\n"
            + "void main()\n"
            + "{\n"
            + "	FragColor =texture(texture1, TexCoord);\n"
            + "}";

    public int vertexShaderId;
    public int fragmentShaderId;
    public int shaderProgramId;

    float y=0.4f;
    float x=0.2f;

    float direcciony=0.0001f;
    float direccionx=0.0001f;

    public void initShaders() {
        vertexShaderId = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        fragmentShaderId = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);

        GL33.glShaderSource(vertexShaderId, vertexShaderSource);
        GL33.glCompileShader(vertexShaderId);

        System.out.println(GL33.glGetShaderInfoLog(vertexShaderId));

        GL33.glShaderSource(fragmentShaderId, fragmentShaderSource);
        GL33.glCompileShader(fragmentShaderId);

        System.out.println(GL33.glGetShaderInfoLog(fragmentShaderId));

        shaderProgramId = GL33.glCreateProgram();
        GL33.glAttachShader(shaderProgramId, vertexShaderId);
        GL33.glAttachShader(shaderProgramId, fragmentShaderId);
        GL33.glLinkProgram(shaderProgramId);

        System.out.println(GL33.glGetProgramInfoLog(shaderProgramId));

        GL33.glDeleteShader(vertexShaderId);
        GL33.glDeleteShader(fragmentShaderId);
        GL33.glUseProgram(shaderProgramId);


    }
    public void setMatrix() {
	
    FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    Move();
    Vector3f vector = new Vector3f(x,y,0.0f);
   
    Matrix4f m=new Matrix4f().translate(vector);
    m.store(matrixBuffer);
    
	matrixBuffer.flip();
	GL20.glUniformMatrix4fv(GL33.glGetUniformLocation(shaderProgramId, "transformationMatrix"), false, matrixBuffer);
   
}

public void Move() {
	
	if(y>0.5f) {
		direcciony=-0.0001f;
	}
	if(y<-0.5f) {
		direcciony=0.0001f;
	}
	if(x>0.5f) {
		direccionx=-0.0001f;
	}
	if(x<-0.5f) {
		direccionx=0.0001f;
	}
	y+=direcciony;
	x+=direccionx;
 }
}
