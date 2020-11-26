import styled from "styled-components";

interface ButtonProps {
  color? : string,
  darkColor?: string
}

export const Button = styled.a`
  display: inline-block;
  border-radius: 5px;
  padding: 0.5rem 0;
  margin: 0.5rem 1rem;
  width: 11rem;

  cursor: pointer;

  background-color: ${(props : ButtonProps) => props.color || "transparent"};
  transition: background-color 0.5s ease;

  &:hover {
    background-color: ${(props : ButtonProps) => props.darkColor || "transparent"};
  }
`