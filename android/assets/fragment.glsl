#ifdef GL_ES
precision mediump float;
precision mediump int;

#endif

varying vec4 v_color;
varying vec2 v_texCoords;
varying vec4 v_pos;

uniform vec2 u_lightPos[10];
uniform vec3 u_lightColor[10];
uniform vec2 u_radius[10];
uniform int u_lightSources;

uniform vec3 u_ambientLight;
uniform float u_ambientIntensity;

uniform sampler2D u_texture;

float rand(vec4 co)
{
    float a = 12.9898;
    float b = 78.233;
    float c = 43758.5453;
    float dt= dot(co.xy ,vec2(a,b));
    float sn= mod(dt,3.14);
    return fract(sin(sn) * c);
}

void main()
{
    vec4 color = (v_color * texture2D(u_texture, v_texCoords));
    vec4 light = vec4(u_ambientLight * u_ambientIntensity, 1);
    for(int i = 0; i < u_lightSources; i++)
    {
        float radius = (v_pos.x - u_lightPos[i].x) * (v_pos.x - u_lightPos[i].x)
                            + (v_pos.y - u_lightPos[i].y) * (v_pos.y - u_lightPos[i].y);
        if(radius < u_radius[i].x)
            light += vec4(u_lightColor[i], 0);
        else if(radius < u_radius[i].y + rand(v_pos))
             light += vec4(0.5 * u_lightColor[i], 0);
    }
    color *= light;
    gl_FragColor = color;
}



